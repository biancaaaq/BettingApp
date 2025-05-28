package proiect.bet.sportbet.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import proiect.bet.sportbet.models.Balanta;
import proiect.bet.sportbet.models.Bilet;
import proiect.bet.sportbet.models.Cota;
import proiect.bet.sportbet.models.Tranzactie;
import proiect.bet.sportbet.models.Utilizator;
import proiect.bet.sportbet.repository.BalantaRepository;
import proiect.bet.sportbet.repository.BiletRepository;
import proiect.bet.sportbet.repository.CotaRepository;
import proiect.bet.sportbet.repository.UtilizatorRepository;
import proiect.bet.sportbet.service.BiletService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/bilete")
@Tag(name = "Bilete", description = "Operațiuni pentru gestionarea biletelor")
public class BiletController {
    private final BiletService biletService;
    private final UtilizatorRepository utilizatorRepository;
    private final CotaRepository cotaRepository;
    private final BalantaRepository balantaRepository;
    private final BiletRepository biletRepository;

    public BiletController(BiletService biletService, UtilizatorRepository utilizatorRepository, 
                           CotaRepository cotaRepository, BalantaRepository balantaRepository, 
                           BiletRepository biletRepository) {
        this.biletService = biletService;
        this.utilizatorRepository = utilizatorRepository;
        this.cotaRepository = cotaRepository;
        this.balantaRepository = balantaRepository;
        this.biletRepository = biletRepository;
    }

    @PostMapping
    @Operation(summary = "Adaugă un bilet nou", description = "Creează un bilet nou în sistem")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Bilet adăugat cu succes"),
        @ApiResponse(responseCode = "403", description = "Acces interzis (necesită rol ADMIN sau USER)"),
        @ApiResponse(responseCode = "400", description = "Fonduri insuficiente pentru a plasa biletul")
    })
    public ResponseEntity<Bilet> createBilet(@RequestBody Bilet bilet) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Utilizator utilizator = utilizatorRepository.findByNumeUtilizator(username)
                .orElseThrow(() -> new RuntimeException("Utilizatorul nu a fost găsit"));
        bilet.setUtilizator(utilizator);

        // Găsește sau creează balanța utilizatorului
        Balanta balanta = balantaRepository.findByUtilizatorId(utilizator.getId())
                .orElseGet(() -> {
                    Balanta newBalanta = new Balanta();
                    newBalanta.setUtilizator(utilizator);
                    newBalanta.setSuma(0.0);
                    return balantaRepository.save(newBalanta);
                });

        // Verifică dacă utilizatorul are suficienți bani pentru miză
        Double miza = bilet.getMiza();
        if (balanta.getSuma() < miza) {
            throw new RuntimeException("Fonduri insuficiente pentru a plasa biletul");
        }

        // Scade miza din balanță
        balanta.setSuma(balanta.getSuma() - miza);
        balantaRepository.save(balanta);

        // Creează o tranzacție de tip WITHDRAWAL pentru miză
        Tranzactie tranzactie = new Tranzactie();
        tranzactie.setUtilizator(utilizator);
        tranzactie.setTip(Tranzactie.TipTranzactie.WITHDRAWAL);
        tranzactie.setValoare(miza);
        tranzactie.setDataTranzactie(LocalDateTime.now());
        biletRepository.save(bilet); // Salvează biletul mai întâi pentru a obține ID-ul

        // Maparea cotelor
        List<Cota> cote = bilet.getCote().stream()
                .map(cota -> cotaRepository.findById(cota.getId())
                        .orElseThrow(() -> new RuntimeException("Cota cu ID-ul " + cota.getId() + " nu a fost găsită")))
                .collect(Collectors.toList());
        bilet.setCote(cote);

        Bilet savedBilet = biletService.createBilet(bilet);
        return ResponseEntity.ok(savedBilet);
    }

    @GetMapping
    @Operation(summary = "Listează toate biletele", description = "Returnează lista tuturor biletelor")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista biletelor returnată"),
        @ApiResponse(responseCode = "403", description = "Acces interzis (necesită rol ADMIN sau USER)")
    })
    public ResponseEntity<List<Bilet>> getAllBilete() {
        List<Bilet> bilete = biletService.getAllBilete();
        return ResponseEntity.ok(bilete);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obține un bilet după ID", description = "Returnează detaliile unui bilet specific")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Bilet găsit"),
        @ApiResponse(responseCode = "404", description = "Biletul nu a fost găsit"),
        @ApiResponse(responseCode = "403", description = "Acces interzis (necesită rol ADMIN sau USER)")
    })
    public ResponseEntity<Bilet> getBiletById(@PathVariable Long id) {
        return biletService.getBiletById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizează un bilet", description = "Actualizează detaliile unui bilet existent")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Bilet actualizat cu succes"),
        @ApiResponse(responseCode = "404", description = "Biletul nu a fost găsit"),
        @ApiResponse(responseCode = "403", description = "Acces interzis (necesită rol ADMIN sau USER)")
    })
    public ResponseEntity<Bilet> updateBilet(@PathVariable Long id, @RequestBody Bilet bilet) {
        try {
            Bilet updatedBilet = biletService.updateBilet(id, bilet);
            return ResponseEntity.ok(updatedBilet);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Șterge un bilet", description = "Șterge un bilet existent din sistem")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Bilet șters cu succes"),
        @ApiResponse(responseCode = "404", description = "Biletul nu a fost găsit"),
        @ApiResponse(responseCode = "403", description = "Acces interzis (necesită rol ADMIN sau USER)")
    })
    public ResponseEntity<Void> deleteBilet(@PathVariable Long id) {
        try {
            biletService.deleteBilet(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/mele")
    @Operation(summary = "Obține biletele utilizatorului", description = "Returnează lista biletelor pentru utilizatorul autentificat")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista biletelor returnată"),
        @ApiResponse(responseCode = "403", description = "Acces interzis (necesită rol USER sau ADMIN)")
    })
    public ResponseEntity<List<Bilet>> getBileteUtilizator() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Utilizator utilizator = utilizatorRepository.findByNumeUtilizator(username)
                .orElseThrow(() -> new RuntimeException("Utilizatorul nu a fost găsit"));
        List<Bilet> bilete = biletRepository.findByUtilizatorId(utilizator.getId());
        return ResponseEntity.ok(bilete);
    }
}