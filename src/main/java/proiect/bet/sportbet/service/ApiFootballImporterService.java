package proiect.bet.sportbet.service;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import proiect.bet.sportbet.models.Cota;
import proiect.bet.sportbet.models.Meci;
import proiect.bet.sportbet.repository.CotaRepository;
import proiect.bet.sportbet.repository.MeciRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;

import java.util.List;

@Service
public class ApiFootballImporterService {

    @Value("${API_FOOTBALL_KEY}")
    private String apiKey;

    private final MeciRepository meciRepository;
    private final CotaRepository cotaRepository;

    public ApiFootballImporterService(MeciRepository meciRepository, CotaRepository cotaRepository) {
        this.meciRepository = meciRepository;
        this.cotaRepository = cotaRepository;
}


    public void importMeciuri() {
    LocalDate azi = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // 🔒 Ligi importante după nume exact
    List<String> ligiImportante = List.of(
        "La Liga", "Bundesliga", "Ligue 1", "Serie A", "Premier League",
        "Liga 1", "Liga Profesional", "Brasileirão", "Primera División"
    );

    HttpHeaders headers = new HttpHeaders();
    headers.set("x-apisports-key", apiKey);
    HttpEntity<String> entity = new HttpEntity<>(headers);
    RestTemplate restTemplate = new RestTemplate();

    int totalMeciuri = 0;
    int meciuriSalvate = 0;

    try {
        for (int i = 0; i < 6; i++) {
            LocalDate zi = azi.plusDays(i);
            String ziFormatata = formatter.format(zi);
            String url = "https://v3.football.api-sports.io/fixtures?date=" + ziFormatata;

            System.out.println(" Verific ziua: " + ziFormatata);
            System.out.println(" Request: " + url);

            ResponseEntity<String> response = restTemplate.exchange(
                url, HttpMethod.GET, entity, String.class
            );

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody());
            JsonNode fixtures = root.path("response");

            for (JsonNode match : fixtures) {
                totalMeciuri++;

            String competitie = match.path("league").path("name").asText();
            String tara = match.path("league").path("country").asText();

            boolean esteLegaRelevanta = (competitie.equals("Premier League") && tara.equals("England")) ||
                (competitie.equals("Ligue 1") && tara.equals("France")) ||
                (competitie.equals("La Liga") && tara.equals("Spain")) ||
                (competitie.equals("Bundesliga") && tara.equals("Germany")) ||
                (competitie.equals("Serie A") && tara.equals("Italy")) ||
                (competitie.equals("Liga 1") && tara.equals("Romania")) ||
                (competitie.equals("Liga Profesional") && tara.equals("Argentina")) ||
                (competitie.equals("Serie A") && tara.equals("Brazil")) ||
                (competitie.equals("Primera División") && tara.equals("Uruguay")) ||
                (competitie.equals("Eredivisie") && tara.equals("Netherlands")) ||
                (competitie.equals("A Lyga") && tara.equals("Lithuania")) ||
                (competitie.equals("Premier League") && tara.equals("Kenya")) ||
                (competitie.equals("Primera Division") && tara.equals("Bolivia")) ||
                (competitie.equals("Premier League") && tara.equals("Ghana")) ||
                (competitie.equals("Major League Soccer") && tara.equals("USA")) ||
                (competitie.equals("J1 League") && tara.equals("Japan")) ||
                (competitie.equals("K League 1") && tara.equals("South Korea")) ||
                (competitie.equals("Allsvenskan") && tara.equals("Sweden")) ||
                (competitie.equals("Veikkausliiga") && tara.equals("Finland"));

if (!esteLegaRelevanta) continue;


                String homeTeam = match.path("teams").path("home").path("name").asText();
                String awayTeam = match.path("teams").path("away").path("name").asText();
                String dateStr = match.path("fixture").path("date").asText();
                OffsetDateTime offsetDateTime = OffsetDateTime.parse(dateStr);
                LocalDateTime date = offsetDateTime.toLocalDateTime();
                String status = match.path("fixture").path("status").path("short").asText();

                if (!meciRepository.existsByEchipaAcasaAndEchipaDeplasareAndDataMeci(homeTeam, awayTeam, date)) {
                    Meci meci = new Meci();
                    meci.setEchipaAcasa(homeTeam);
                    meci.setEchipaDeplasare(awayTeam);
                    meci.setDataMeci(date);
                    meci.setCompetitie(competitie);
                    meci.setBlocat(false);
                    meci.setRezultat(null);
                    meci.setStatus(status);
                    meciRepository.save(meci);
                    importCotePentruMeci(meci, match.get("fixture").get("id").asInt());
                    meciuriSalvate++;
                }
            }
        }

        System.out.println("Total meciuri primite de la API: " + totalMeciuri);
        System.out.println("Meciuri salvate în baza de date: " + meciuriSalvate);

    } catch (Exception e) {
        System.err.println("Eroare la importul meciurilor:");
        e.printStackTrace();
    }
}

    private void importCotePentruMeci(Meci meci, int fixtureId) {
    String url = "https://v3.football.api-sports.io/odds?fixture=" + fixtureId;
    HttpHeaders headers = new HttpHeaders();
    headers.set("x-apisports-key", apiKey);
    HttpEntity<String> entity = new HttpEntity<>(headers);
    RestTemplate restTemplate = new RestTemplate();

    try {
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getBody());
        JsonNode oddsData = root.path("response");

        if (!oddsData.isArray() || oddsData.isEmpty()) {
            System.out.println("⚠️ Nicio cotă găsită pentru meciul " + meci.getEchipaAcasa() + " - " + meci.getEchipaDeplasare());
            return;
        }

        // Luăm prima casă de pariuri, tipic e ok
        JsonNode bets = oddsData.get(0).path("bookmakers").get(0).path("bets");

        for (JsonNode bet : bets) {
            if (!bet.path("name").asText().equalsIgnoreCase("Match Winner")) continue;

            for (JsonNode cota : bet.path("values")) {
                String tip = cota.path("value").asText(); // 1, X, 2
                double valoare = cota.path("odd").asDouble();

                String descriere;
                if (tip.equals("Home")) descriere = meci.getEchipaAcasa() + " câștigă";
                else if (tip.equals("Away")) descriere = meci.getEchipaDeplasare() + " câștigă";
                else if (tip.equals("Draw")) descriere = "Egal";
                else continue;

                Cota c = new Cota();
                c.setBlocat(false);
                c.setDescriere(descriere);
                c.setValoare(valoare);
                c.setMeci(meci); // asigură-te că ID-ul e setat în `meci`
                cotaRepository.save(c);
            }
        }

        System.out.println("Cote importate pentru meciul " + meci.getEchipaAcasa() + " - " + meci.getEchipaDeplasare());

    } catch (Exception e) {
        System.err.println(" Eroare la importul cotelor pentru fixture " + fixtureId);
        e.printStackTrace();
    }
}


}

