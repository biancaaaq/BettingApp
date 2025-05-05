package proiect.bet.sportbet.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import proiect.bet.sportbet.models.DetaliuBilet;
import proiect.bet.sportbet.service.DetaliuBiletService;

import java.util.List;

@RestController
@RequestMapping("/detalii-bilete")
@Tag(name = "Detalii Bilete", description = "Operațiuni pentru gestionarea detaliilor biletelor")
public class DetaliuBiletController {
    private final DetaliuBiletService detaliuBiletService;

    public DetaliuBiletController(DetaliuBiletService detaliuBiletService) {
        this.detaliuBiletService = detaliuBiletService;
    }

    @PostMapping
    @Operation(summary = "Adaugă un detaliu bilet nou", description = "Creează un detaliu bilet nou în sistem")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Detaliu bilet adăugat cu succes"),
        @ApiResponse(responseCode = "403", description = "Acces interzis (necesită rol ADMIN sau USER)")
    })
    public ResponseEntity<DetaliuBilet> createDetaliuBilet(@RequestBody DetaliuBilet detaliuBilet) {
        DetaliuBilet savedDetaliuBilet = detaliuBiletService.createDetaliuBilet(detaliuBilet);
        return ResponseEntity.ok(savedDetaliuBilet);
    }

    @GetMapping
    @Operation(summary = "Listează toate detaliile biletelor", description = "Returnează lista tuturor detaliilor biletelor")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista detaliilor biletelor returnată"),
        @ApiResponse(responseCode = "403", description = "Acces interzis (necesită rol ADMIN sau USER)")
    })
    public ResponseEntity<List<DetaliuBilet>> getAllDetaliiBilete() {
        List<DetaliuBilet> detaliiBilete = detaliuBiletService.getAllDetaliiBilete();
        return ResponseEntity.ok(detaliiBilete);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obține un detaliu bilet după ID", description = "Returnează detaliile unui detaliu bilet specific")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Detaliu bilet găsit"),
        @ApiResponse(responseCode = "404", description = "Detaliul bilet nu a fost găsit"),
        @ApiResponse(responseCode = "403", description = "Acces interzis (necesită rol ADMIN sau USER)")
    })
    public ResponseEntity<DetaliuBilet> getDetaliuBiletById(@PathVariable Long id) {
        return detaliuBiletService.getDetaliuBiletById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizează un detaliu bilet", description = "Actualizează detaliile unui detaliu bilet existent")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Detaliu bilet actualizat cu succes"),
        @ApiResponse(responseCode = "404", description = "Detaliul bilet nu a fost găsit"),
        @ApiResponse(responseCode = "403", description = "Acces interzis (necesită rol ADMIN sau USER)")
    })
    public ResponseEntity<DetaliuBilet> updateDetaliuBilet(@PathVariable Long id, @RequestBody DetaliuBilet detaliuBilet) {
        try {
            DetaliuBilet updatedDetaliuBilet = detaliuBiletService.updateDetaliuBilet(id, detaliuBilet);
            return ResponseEntity.ok(updatedDetaliuBilet);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Șterge un detaliu bilet", description = "Șterge un detaliu bilet existent din sistem")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Detaliu bilet șters cu succes"),
        @ApiResponse(responseCode = "404", description = "Detaliul bilet nu a fost găsit"),
        @ApiResponse(responseCode = "403", description = "Acces interzis (necesită rol ADMIN sau USER)")
    })
    public ResponseEntity<Void> deleteDetaliuBilet(@PathVariable Long id) {
        try {
            detaliuBiletService.deleteDetaliuBilet(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}