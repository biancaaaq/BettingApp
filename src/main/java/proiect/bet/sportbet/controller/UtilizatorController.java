package proiect.bet.sportbet.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import proiect.bet.sportbet.models.Utilizator;
import proiect.bet.sportbet.service.UtilizatorService;

import java.util.List;

@RestController
@RequestMapping("/utilizatori")
@Tag(name = "Utilizatori", description = "Operațiuni pentru gestionarea utilizatorilor")
public class UtilizatorController {
    private final UtilizatorService utilizatorService;

    public UtilizatorController(UtilizatorService utilizatorService, PasswordEncoder passwordEncoder) {
        this.utilizatorService = utilizatorService;
    }

    @PostMapping
    @Operation(summary = "Adaugă un utilizator nou", description = "Creează un utilizator nou în sistem")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Utilizator adăugat cu succes"),
        @ApiResponse(responseCode = "403", description = "Acces interzis (necesită rol ADMIN)")
    })
    public ResponseEntity<Utilizator> createUtilizator(@RequestBody Utilizator utilizator) {
        Utilizator savedUtilizator = utilizatorService.createUtilizator(utilizator);
        return ResponseEntity.ok(savedUtilizator);
    }

    @GetMapping
    @Operation(summary = "Listează toți utilizatorii", description = "Returnează lista tuturor utilizatorilor (doar pentru ADMIN)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista utilizatorilor returnată"),
        @ApiResponse(responseCode = "403", description = "Acces interzis (necesită rol ADMIN)")
    })
    public ResponseEntity<List<Utilizator>> getAllUtilizatori() {
        List<Utilizator> utilizatori = utilizatorService.getAllUtilizatori();
        return ResponseEntity.ok(utilizatori);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obține un utilizator după ID", description = "Returnează detaliile unui utilizator specific")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Utilizator găsit"),
        @ApiResponse(responseCode = "404", description = "Utilizatorul nu a fost găsit"),
        @ApiResponse(responseCode = "403", description = "Acces interzis (necesită rol ADMIN sau USER)")
    })
    public ResponseEntity<Utilizator> getUtilizatorById(@PathVariable Long id) {
        return utilizatorService.getUtilizatorById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizează un utilizator", description = "Actualizează detaliile unui utilizator existent")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Utilizator actualizat cu succes"),
        @ApiResponse(responseCode = "404", description = "Utilizatorul nu a fost găsit"),
        @ApiResponse(responseCode = "403", description = "Acces interzis (necesită rol ADMIN)")
    })
    public ResponseEntity<Utilizator> updateUtilizator(@PathVariable Long id, @RequestBody Utilizator utilizator) {
        try {
            Utilizator updatedUtilizator = utilizatorService.updateUtilizator(id, utilizator);
            return ResponseEntity.ok(updatedUtilizator);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Șterge un utilizator", description = "Șterge un utilizator existent din sistem")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Utilizator șters cu succes"),
        @ApiResponse(responseCode = "404", description = "Utilizatorul nu a fost găsit"),
        @ApiResponse(responseCode = "403", description = "Acces interzis (necesită rol ADMIN)")
    })
    public ResponseEntity<Void> deleteUtilizator(@PathVariable Long id) {
        try {
            utilizatorService.deleteUtilizator(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}