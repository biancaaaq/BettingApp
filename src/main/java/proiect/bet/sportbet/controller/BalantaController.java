package proiect.bet.sportbet.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import proiect.bet.sportbet.models.Balanta;
import proiect.bet.sportbet.service.BalantaService;

import java.util.List;

@RestController
@RequestMapping("/balanta")
@Tag(name = "Balanțe", description = "Operațiuni pentru gestionarea balanțelor")
public class BalantaController {
    private final BalantaService balantaService;

    public BalantaController(BalantaService balantaService) {
        this.balantaService = balantaService;
    }

    @PostMapping
    @Operation(summary = "Adaugă o balanță nouă", description = "Creează o balanță nouă în sistem")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Balanță adăugată cu succes"),
        @ApiResponse(responseCode = "403", description = "Acces interzis (necesită rol ADMIN sau USER)")
    })
    public ResponseEntity<Balanta> createBalanta(@RequestBody Balanta balanta) {
        Balanta savedBalanta = balantaService.createBalanta(balanta);
        return ResponseEntity.ok(savedBalanta);
    }

    @GetMapping
    @Operation(summary = "Listează toate balanțele", description = "Returnează lista tuturor balanțelor")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista balanțelor returnată"),
        @ApiResponse(responseCode = "403", description = "Acces interzis (necesită rol ADMIN sau USER)")
    })
    public ResponseEntity<List<Balanta>> getAllBalante() {
        List<Balanta> balante = balantaService.getAllBalante();
        return ResponseEntity.ok(balante);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obține o balanță după ID", description = "Returnează detaliile unei balanțe specifice")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Balanță găsită"),
        @ApiResponse(responseCode = "404", description = "Balanța nu a fost găsită"),
        @ApiResponse(responseCode = "403", description = "Acces interzis (necesită rol ADMIN sau USER)")
    })
    public ResponseEntity<Balanta> getBalantaById(@PathVariable Long id) {
        return balantaService.getBalantaById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizează o balanță", description = "Actualizează detaliile unei balanțe existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Balanță actualizată cu succes"),
        @ApiResponse(responseCode = "404", description = "Balanța nu a fost găsită"),
        @ApiResponse(responseCode = "403", description = "Acces interzis (necesită rol ADMIN sau USER)")
    })
    public ResponseEntity<Balanta> updateBalanta(@PathVariable Long id, @RequestBody Balanta balanta) {
        try {
            Balanta updatedBalanta = balantaService.updateBalanta(id, balanta);
            return ResponseEntity.ok(updatedBalanta);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Șterge o balanță", description = "Șterge o balanță existentă din sistem")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Balanță ștearsă cu succes"),
        @ApiResponse(responseCode = "404", description = "Balanța nu a fost găsită"),
        @ApiResponse(responseCode = "403", description = "Acces interzis (necesită rol ADMIN sau USER)")
    })
    public ResponseEntity<Void> deleteBalanta(@PathVariable Long id) {
        try {
            balantaService.deleteBalanta(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}