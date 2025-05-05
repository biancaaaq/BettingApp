package proiect.bet.sportbet.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import proiect.bet.sportbet.models.Tranzactie;
import proiect.bet.sportbet.service.TranzactieService;

import java.util.List;

@RestController
@RequestMapping("/tranzactii")
@Tag(name = "Tranzacții", description = "Operațiuni pentru gestionarea tranzacțiilor")
public class TranzactieController {
    private final TranzactieService tranzactieService;

    public TranzactieController(TranzactieService tranzactieService) {
        this.tranzactieService = tranzactieService;
    }

    @PostMapping
    @Operation(summary = "Adaugă o tranzacție nouă", description = "Creează o tranzacție nouă în sistem")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tranzacție adăugată cu succes"),
        @ApiResponse(responseCode = "403", description = "Acces interzis (necesită rol ADMIN sau USER)")
    })
    public ResponseEntity<Tranzactie> createTranzactie(@RequestBody Tranzactie tranzactie) {
        Tranzactie savedTranzactie = tranzactieService.createTranzactie(tranzactie);
        return ResponseEntity.ok(savedTranzactie);
    }

    @GetMapping
    @Operation(summary = "Listează toate tranzacțiile", description = "Returnează lista tuturor tranzacțiilor")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista tranzacțiilor returnată"),
        @ApiResponse(responseCode = "403", description = "Acces interzis (necesită rol ADMIN sau USER)")
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
        @ApiResponse(responseCode = "403", description = "Acces interzis (necesită rol ADMIN sau USER)")
    })
    public ResponseEntity<Tranzactie> getTranzactieById(@PathVariable Long id) {
        return tranzactieService.getTranzactieById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizează o tranzacție", description = "Actualizează detaliile unei tranzacții existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tranzacție actualizată cu succes"),
        @ApiResponse(responseCode = "404", description = "Tranzacția nu a fost găsită"),
        @ApiResponse(responseCode = "403", description = "Acces interzis (necesită rol ADMIN sau USER)")
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
        @ApiResponse(responseCode = "403", description = "Acces interzis (necesită rol ADMIN sau USER)")
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