package proiect.bet.sportbet.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import proiect.bet.sportbet.models.CerereAutoexcludere;
import proiect.bet.sportbet.models.Tranzactie;
import proiect.bet.sportbet.models.Utilizator;
import proiect.bet.sportbet.repository.UtilizatorRepository;
import proiect.bet.sportbet.service.CerereAutoexcludereService;
import proiect.bet.sportbet.service.TranzactieService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/contul-meu")
@Tag(name = "Contul Meu", description = "Informații despre utilizatorul curent")
public class ContController {
    private final TranzactieService tranzactieService;
    private final CerereAutoexcludereService cerereAutoexcludereService;
    private final UtilizatorRepository utilizatorRepository;

    public ContController(TranzactieService tranzactieService, CerereAutoexcludereService cerereAutoexcludereService, UtilizatorRepository utilizatorRepository) {
        this.tranzactieService = tranzactieService;
        this.cerereAutoexcludereService = cerereAutoexcludereService;
        this.utilizatorRepository = utilizatorRepository;
    }

    @GetMapping
    @Operation(summary = "Obține detalii cont", description = "Returnează informațiile despre contul utilizatorului autentificat")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Datele contului returnate cu succes"),
        @ApiResponse(responseCode = "403", description = "Acces interzis")
    })
    public ResponseEntity<?> getContulMeu() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Utilizator utilizator = utilizatorRepository.findByNumeUtilizator(username)
                .orElseThrow(() -> new RuntimeException("Utilizatorul nu a fost găsit"));

        Double balanta = tranzactieService.getBalantaUtilizator(utilizator.getId());
        List<Tranzactie> tranzactii = tranzactieService.getTranzactiiByUtilizatorId(utilizator.getId());
        Optional<CerereAutoexcludere> cerereAutoexcludere = cerereAutoexcludereService.getCerereAprobataByUtilizatorId(utilizator.getId());

        return ResponseEntity.ok(new ContDTO(utilizator.getNumeUtilizator(), utilizator.getEmail(), balanta, tranzactii, cerereAutoexcludere.orElse(null)));
    }

    // DTO intern pentru returnarea detaliilor contului
    public static class ContDTO {
        public String nume;
        public String email;
        public Double balanta;
        public List<Tranzactie> tranzactii;
        public CerereAutoexcludere autoexcludere;

        public ContDTO(String nume, String email, Double balanta, List<Tranzactie> tranzactii, CerereAutoexcludere autoexcludere) {
            this.nume = nume;
            this.email = email;
            this.balanta = balanta;
            this.tranzactii = tranzactii;
            this.autoexcludere = autoexcludere;
        }
    }
}