package proiect.bet.sportbet.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import proiect.bet.sportbet.models.Promotie;
import proiect.bet.sportbet.service.PromotieService;

import java.util.List;

@RestController
@RequestMapping("/promotii")
@Tag(name = "Promoții", description = "Operațiuni pentru gestionarea promoțiilor")
public class PromotieController {
    private final PromotieService promotieService;

    public PromotieController(PromotieService promotieService) {
        this.promotieService = promotieService;
    }

    @PostMapping
    @Operation(summary = "Adaugă o promoție nouă", description = "Creează o promoție nouă în sistem")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Promoție adăugată cu succes"),
        @ApiResponse(responseCode = "403", description = "Acces interzis (necesită rol ADMIN sau USER)")
    })
    public ResponseEntity<Promotie> createPromotie(@RequestBody Promotie promotie) {
        Promotie savedPromotie = promotieService.createPromotie(promotie);
        return ResponseEntity.ok(savedPromotie);
    }

    @GetMapping
    @Operation(summary = "Listează toate promoțiile", description = "Returnează lista tuturor promoțiilor")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista promoțiilor returnată"),
        @ApiResponse(responseCode = "403", description = "Acces interzis (necesită rol ADMIN sau USER)")
    })
    public ResponseEntity<List<Promotie>> getAllPromotii() {
        List<Promotie> promotii = promotieService.getAllPromotii();
        return ResponseEntity.ok(promotii);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obține o promoție după ID", description = "Returnează detaliile unei promoții specifice")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Promoție găsită"),
        @ApiResponse(responseCode = "404", description = "Promoția nu a fost găsită"),
        @ApiResponse(responseCode = "403", description = "Acces interzis (necesită rol ADMIN sau USER)")
    })
    public ResponseEntity<Promotie> getPromotieById(@PathVariable Long id) {
        return promotieService.getPromotieById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizează o promoție", description = "Actualizează detaliile unei promoții existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Promoție actualizată cu succes"),
        @ApiResponse(responseCode = "404", description = "Promoția nu a fost găsită"),
        @ApiResponse(responseCode = "403", description = "Acces interzis (necesită rol ADMIN sau USER)")
    })
    public ResponseEntity<Promotie> updatePromotie(@PathVariable Long id, @RequestBody Promotie promotie) {
        try {
            Promotie updatedPromotie = promotieService.updatePromotie(id, promotie);
            return ResponseEntity.ok(updatedPromotie);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Șterge o promoție", description = "Șterge o promoție existentă din sistem")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Promoție ștearsă cu succes"),
        @ApiResponse(responseCode = "404", description = "Promoția nu a fost găsită"),
        @ApiResponse(responseCode = "403", description = "Acces interzis (necesită rol ADMIN sau USER)")
    })
    public ResponseEntity<Void> deletePromotie(@PathVariable Long id) {
        try {
            promotieService.deletePromotie(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}