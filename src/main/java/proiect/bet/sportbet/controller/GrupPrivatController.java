package proiect.bet.sportbet.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import proiect.bet.sportbet.models.GrupPrivat;
import proiect.bet.sportbet.service.GrupPrivatService;

import java.util.List;

@RestController
@RequestMapping("/grupuri-private")
@Tag(name = "Grupuri Private", description = "Operațiuni pentru gestionarea grupurilor private")
public class GrupPrivatController {
    private final GrupPrivatService grupPrivatService;

    public GrupPrivatController(GrupPrivatService grupPrivatService) {
        this.grupPrivatService = grupPrivatService;
    }

    @PostMapping
    @Operation(summary = "Adaugă un grup privat nou", description = "Creează un grup privat nou în sistem")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Grup privat adăugat cu succes"),
        @ApiResponse(responseCode = "403", description = "Acces interzis (necesită rol ADMIN sau USER)")
    })
    public ResponseEntity<GrupPrivat> createGrupPrivat(@RequestBody GrupPrivat grupPrivat) {
        GrupPrivat savedGrupPrivat = grupPrivatService.createGrupPrivat(grupPrivat);
        return ResponseEntity.ok(savedGrupPrivat);
    }

    @GetMapping
    @Operation(summary = "Listează toate grupurile private", description = "Returnează lista tuturor grupurilor private")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista grupurilor private returnată"),
        @ApiResponse(responseCode = "403", description = "Acces interzis (necesită rol ADMIN sau USER)")
    })
    public ResponseEntity<List<GrupPrivat>> getAllGrupuriPrivate() {
        List<GrupPrivat> grupuriPrivate = grupPrivatService.getAllGrupuriPrivate();
        return ResponseEntity.ok(grupuriPrivate);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obține un grup privat după ID", description = "Returnează detaliile unui grup privat specific")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Grup privat găsit"),
        @ApiResponse(responseCode = "404", description = "Grupul privat nu a fost găsit"),
        @ApiResponse(responseCode = "403", description = "Acces interzis (necesită rol ADMIN sau USER)")
    })
    public ResponseEntity<GrupPrivat> getGrupPrivatById(@PathVariable Long id) {
        return grupPrivatService.getGrupPrivatById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizează un grup privat", description = "Actualizează detaliile unui grup privat existent")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Grup privat actualizat cu succes"),
        @ApiResponse(responseCode = "404", description = "Grupul privat nu a fost găsit"),
        @ApiResponse(responseCode = "403", description = "Acces interzis (necesită rol ADMIN sau USER)")
    })
    public ResponseEntity<GrupPrivat> updateGrupPrivat(@PathVariable Long id, @RequestBody GrupPrivat grupPrivat) {
        try {
            GrupPrivat updatedGrupPrivat = grupPrivatService.updateGrupPrivat(id, grupPrivat);
            return ResponseEntity.ok(updatedGrupPrivat);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Șterge un grup privat", description = "Șterge un grup privat existent din sistem")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Grup privat șters cu succes"),
        @ApiResponse(responseCode = "404", description = "Grupul privat nu a fost găsit"),
        @ApiResponse(responseCode = "403", description = "Acces interzis (necesită rol ADMIN sau USER)")
    })
    public ResponseEntity<Void> deleteGrupPrivat(@PathVariable Long id) {
        try {
            grupPrivatService.deleteGrupPrivat(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}