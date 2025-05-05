package proiect.bet.sportbet.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import proiect.bet.sportbet.models.RolUtilizator;
import proiect.bet.sportbet.service.RolUtilizatorService;

import java.util.List;

@RestController
@RequestMapping("/rol-utilizatori")
@Tag(name = "Roluri Utilizatori", description = "Operațiuni pentru gestionarea rolurilor utilizatorilor")
public class RolUtilizatorController {
    private final RolUtilizatorService rolUtilizatorService;

    public RolUtilizatorController(RolUtilizatorService rolUtilizatorService) {
        this.rolUtilizatorService = rolUtilizatorService;
    }

    @PostMapping
    @Operation(summary = "Adaugă un rol utilizator nou", description = "Creează un rol utilizator nou în sistem")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Rol utilizator adăugat cu succes"),
        @ApiResponse(responseCode = "403", description = "Acces interzis (necesită rol ADMIN sau USER)")
    })
    public ResponseEntity<RolUtilizator> createRolUtilizator(@RequestBody RolUtilizator rolUtilizator) {
        RolUtilizator savedRolUtilizator = rolUtilizatorService.createRolUtilizator(rolUtilizator);
        return ResponseEntity.ok(savedRolUtilizator);
    }

    @GetMapping
    @Operation(summary = "Listează toate rolurile utilizatorilor", description = "Returnează lista tuturor rolurilor utilizatorilor")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista rolurilor utilizatorilor returnată"),
        @ApiResponse(responseCode = "403", description = "Acces interzis (necesită rol ADMIN sau USER)")
    })
    public ResponseEntity<List<RolUtilizator>> getAllRolUtilizatori() {
        List<RolUtilizator> rolUtilizatori = rolUtilizatorService.getAllRolUtilizatori();
        return ResponseEntity.ok(rolUtilizatori);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obține un rol utilizator după ID", description = "Returnează detaliile unui rol utilizator specific")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Rol utilizator găsit"),
        @ApiResponse(responseCode = "404", description = "Rolul utilizator nu a fost găsit"),
        @ApiResponse(responseCode = "403", description = "Acces interzis (necesită rol ADMIN sau USER)")
    })
    public ResponseEntity<RolUtilizator> getRolUtilizatorById(@PathVariable Long id) {
        return rolUtilizatorService.getRolUtilizatorById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizează un rol utilizator", description = "Actualizează detaliile unui rol utilizator existent")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Rol utilizator actualizat cu succes"),
        @ApiResponse(responseCode = "404", description = "Rolul utilizator nu a fost găsit"),
        @ApiResponse(responseCode = "403", description = "Acces interzis (necesită rol ADMIN sau USER)")
    })
    public ResponseEntity<RolUtilizator> updateRolUtilizator(@PathVariable Long id, @RequestBody RolUtilizator rolUtilizator) {
        try {
            RolUtilizator updatedRolUtilizator = rolUtilizatorService.updateRolUtilizator(id, rolUtilizator);
            return ResponseEntity.ok(updatedRolUtilizator);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Șterge un rol utilizator", description = "Șterge un rol utilizator existent din sistem")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Rol utilizator șters cu succes"),
        @ApiResponse(responseCode = "404", description = "Rolul utilizator nu a fost găsit"),
        @ApiResponse(responseCode = "403", description = "Acces interzis (necesită rol ADMIN sau USER)")
    })
    public ResponseEntity<Void> deleteRolUtilizator(@PathVariable Long id) {
        try {
            rolUtilizatorService.deleteRolUtilizator(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}