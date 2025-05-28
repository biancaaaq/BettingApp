package proiect.bet.sportbet.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import proiect.bet.sportbet.models.Tranzactie;
import proiect.bet.sportbet.models.Utilizator;
import proiect.bet.sportbet.repository.UtilizatorRepository;
import proiect.bet.sportbet.service.TranzactieService;

import java.util.List;

@RestController
@RequestMapping("/tranzactii")
@Tag(name = "Tranzactii", description = "Operațiuni pentru gestionarea tranzacțiilor")
public class TranzactieController {
    private final TranzactieService tranzactieService;
    private final UtilizatorRepository utilizatorRepository;

    public TranzactieController(TranzactieService tranzactieService, UtilizatorRepository utilizatorRepository) {
        this.tranzactieService = tranzactieService;
        this.utilizatorRepository = utilizatorRepository;
    }

    @PostMapping("/depunere")
    @Operation(summary = "Depune bani", description = "Creează o tranzacție de depunere pentru utilizatorul autentificat")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Depunere realizată cu succes"),
        @ApiResponse(responseCode = "403", description = "Acces interzis (necesită rol USER sau ADMIN)")
    })
    public ResponseEntity<Tranzactie> depunere(@RequestBody Tranzactie tranzactie) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Utilizator utilizator = utilizatorRepository.findByNumeUtilizator(username)
                .orElseThrow(() -> new RuntimeException("Utilizatorul nu a fost găsit"));
        tranzactie.setUtilizator(utilizator);
        tranzactie.setTip(Tranzactie.TipTranzactie.DEPOSIT);
        Tranzactie savedTranzactie = tranzactieService.createTranzactie(tranzactie);
        return ResponseEntity.ok(savedTranzactie);
    }

    @PostMapping("/retragere")
    @Operation(summary = "Extrage bani", description = "Creează o tranzacție de retragere pentru utilizatorul autentificat")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Retragere realizată cu succes"),
        @ApiResponse(responseCode = "403", description = "Acces interzis (necesită rol USER sau ADMIN)"),
        @ApiResponse(responseCode = "400", description = "Fonduri insuficiente pentru retragere")
    })
    public ResponseEntity<Tranzactie> retragere(@RequestBody Tranzactie tranzactie) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Utilizator utilizator = utilizatorRepository.findByNumeUtilizator(username)
                .orElseThrow(() -> new RuntimeException("Utilizatorul nu a fost găsit"));
        tranzactie.setUtilizator(utilizator);
        tranzactie.setTip(Tranzactie.TipTranzactie.WITHDRAWAL);
        Tranzactie savedTranzactie = tranzactieService.createTranzactie(tranzactie);
        return ResponseEntity.ok(savedTranzactie);
    }

    @GetMapping("/balanta")
    @Operation(summary = "Obține balanța utilizatorului", description = "Returnează balanța curentă a utilizatorului (autentificat sau specificat prin utilizatorId)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Balanța returnată"),
        @ApiResponse(responseCode = "403", description = "Acces interzis (necesită rol USER sau ADMIN)"),
        @ApiResponse(responseCode = "404", description = "Utilizatorul nu a fost găsit")
    })
    public ResponseEntity<Double> getBalanta(@RequestParam(value = "utilizatorId", required = false) Long utilizatorId) {
        Long id;
        if (utilizatorId != null) {
            // Admin-ul cere balanța pentru un utilizator specific
            Utilizator utilizator = utilizatorRepository.findById(utilizatorId)
                    .orElseThrow(() -> new RuntimeException("Utilizatorul nu a fost găsit"));
            id = utilizator.getId();
        } else {
            // Utilizatorul autentificat cere propria balanță
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            Utilizator utilizator = utilizatorRepository.findByNumeUtilizator(username)
                    .orElseThrow(() -> new RuntimeException("Utilizatorul nu a fost găsit"));
            id = utilizator.getId();
        }
        Double balanta = tranzactieService.getBalantaUtilizator(id);
        return ResponseEntity.ok(balanta);
    }

    @GetMapping
    @Operation(summary = "Listează toate tranzacțiile", description = "Returnează lista tuturor tranzacțiilor")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista tranzacțiilor returnată"),
        @ApiResponse(responseCode = "403", description = "Acces interzis (necesită rol USER sau ADMIN)")
    })
    public ResponseEntity<List<Tranzactie>> getAllTranzactii() {
        List<Tranzactie> tranzactii = tranzactieService.getAllTranzactii();
        return ResponseEntity.ok(tranzactii);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obține o tranzacție după ID", description = "Returnează detaliile unei tranzacții specifice")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tranzacție găsită"),
        @ApiResponse(responseCode = "404", description = "Tranzacția nu a fost găsită"),
        @ApiResponse(responseCode = "403", description = "Acces interzis (necesită rol USER sau ADMIN)")
    })
    public ResponseEntity<Tranzactie> getTranzactieById(@PathVariable Long id) {
        return tranzactieService.getTranzactieById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/utilizator")
    @Operation(summary = "Obține tranzacțiile utilizatorului", description = "Returnează lista tranzacțiilor pentru utilizatorul autentificat")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista tranzacțiilor returnată"),
        @ApiResponse(responseCode = "403", description = "Acces interzis (necesită rol USER sau ADMIN)")
    })
    public ResponseEntity<List<Tranzactie>> getTranzactiiUtilizator() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Utilizator utilizator = utilizatorRepository.findByNumeUtilizator(username)
                .orElseThrow(() -> new RuntimeException("Utilizatorul nu a fost găsit"));
        List<Tranzactie> tranzactii = tranzactieService.getTranzactiiByUtilizatorId(utilizator.getId());
        return ResponseEntity.ok(tranzactii);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizează o tranzacție", description = "Actualizează detaliile unei tranzacții existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tranzacție actualizată cu succes"),
        @ApiResponse(responseCode = "404", description = "Tranzacția nu a fost găsită"),
        @ApiResponse(responseCode = "403", description = "Acces interzis (necesită rol USER sau ADMIN)")
    })
    public ResponseEntity<Tranzactie> updateTranzactie(@PathVariable Long id, @RequestBody Tranzactie tranzactie) {
        try {
            Tranzactie updatedTranzactie = tranzactieService.updateTranzactie(id, tranzactie);
            return ResponseEntity.ok(updatedTranzactie);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Șterge o tranzacție", description = "Șterge o tranzacție existentă din sistem")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Tranzacție ștearsă cu succes"),
        @ApiResponse(responseCode = "404", description = "Tranzacția nu a fost găsită"),
        @ApiResponse(responseCode = "403", description = "Acces interzis (necesită rol USER sau ADMIN)")
    })
    public ResponseEntity<Void> deleteTranzactie(@PathVariable Long id) {
        try {
            tranzactieService.deleteTranzactie(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}