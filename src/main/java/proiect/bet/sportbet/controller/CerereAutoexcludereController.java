package proiect.bet.sportbet.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import proiect.bet.sportbet.models.CerereAutoexcludere;
import proiect.bet.sportbet.service.CerereAutoexcludereService;

import java.util.List;

@RestController
@RequestMapping("/cereri-autoexcludere")
@Tag(name = "Cereri Autoexcludere", description = "Operațiuni pentru gestionarea cererilor de autoexcludere")
public class CerereAutoexcludereController {
    private final CerereAutoexcludereService cerereAutoexcludereService;

    public CerereAutoexcludereController(CerereAutoexcludereService cerereAutoexcludereService) {
        this.cerereAutoexcludereService = cerereAutoexcludereService;
    }

    @PostMapping
    @Operation(summary = "Adaugă o cerere de autoexcludere nouă", description = "Creează o cerere de autoexcludere nouă în sistem")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cerere de autoexcludere adăugată cu succes"),
        @ApiResponse(responseCode = "403", description = "Acces interzis (necesită rol ADMIN sau USER)")
    })
    public ResponseEntity<CerereAutoexcludere> createCerereAutoexcludere(@RequestBody CerereAutoexcludere cerereAutoexcludere) {
        CerereAutoexcludere savedCerereAutoexcludere = cerereAutoexcludereService.createCerereAutoexcludere(cerereAutoexcludere);
        return ResponseEntity.ok(savedCerereAutoexcludere);
    }

    @GetMapping
    @Operation(summary = "Listează toate cererile de autoexcludere", description = "Returnează lista tuturor cererilor de autoexcludere")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista cererilor de autoexcludere returnată"),
        @ApiResponse(responseCode = "403", description = "Acces interzis (necesită rol ADMIN sau USER)")
    })
    public ResponseEntity<List<CerereAutoexcludere>> getAllCereriAutoexcludere() {
        List<CerereAutoexcludere> cereriAutoexcludere = cerereAutoexcludereService.getAllCereriAutoexcludere();
        return ResponseEntity.ok(cereriAutoexcludere);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obține o cerere de autoexcludere după ID", description = "Returnează detaliile unei cereri de autoexcludere specifice")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cerere de autoexcludere găsită"),
        @ApiResponse(responseCode = "404", description = "Cererea de autoexcludere nu a fost găsită"),
        @ApiResponse(responseCode = "403", description = "Acces interzis (necesită rol ADMIN sau USER)")
    })
    public ResponseEntity<CerereAutoexcludere> getCerereAutoexcludereById(@PathVariable Long id) {
        return cerereAutoexcludereService.getCerereAutoexcludereById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizează o cerere de autoexcludere", description = "Actualizează detaliile unei cereri de autoexcludere existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cerere de autoexcludere actualizată cu succes"),
        @ApiResponse(responseCode = "404", description = "Cererea de autoexcludere nu a fost găsită"),
        @ApiResponse(responseCode = "403", description = "Acces interzis (necesită rol ADMIN sau USER)")
    })
    public ResponseEntity<CerereAutoexcludere> updateCerereAutoexcludere(@PathVariable Long id, @RequestBody CerereAutoexcludere cerereAutoexcludere) {
        try {
            CerereAutoexcludere updatedCerereAutoexcludere = cerereAutoexcludereService.updateCerereAutoexcludere(id, cerereAutoexcludere);
            return ResponseEntity.ok(updatedCerereAutoexcludere);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Șterge o cerere de autoexcludere", description = "Șterge o cerere de autoexcludere existentă din sistem")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Cerere de autoexcludere ștearsă cu succes"),
        @ApiResponse(responseCode = "404", description = "Cererea de autoexcludere nu a fost găsită"),
        @ApiResponse(responseCode = "403", description = "Acces interzis (necesită rol ADMIN sau USER)")
    })
    public ResponseEntity<Void> deleteCerereAutoexcludere(@PathVariable Long id) {
        try {
            cerereAutoexcludereService.deleteCerereAutoexcludere(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}