package proiect.bet.sportbet.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import proiect.bet.sportbet.models.Cota;
import proiect.bet.sportbet.service.CotaService;

import java.util.List;

@RestController
@RequestMapping("/cote")
@Tag(name = "Cote", description = "Operațiuni pentru gestionarea cotelor")
public class CotaController {
    private final CotaService cotaService;

    public CotaController(CotaService cotaService) {
        this.cotaService = cotaService;
    }

    @PostMapping
    @Operation(summary = "Adaugă o cotă nouă", description = "Creează o cotă nouă în sistem")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cotă adăugată cu succes"),
        @ApiResponse(responseCode = "403", description = "Acces interzis (necesită rol ADMIN sau USER)")
    })
    public ResponseEntity<Cota> createCota(@RequestBody Cota cota) {
        Cota savedCota = cotaService.createCota(cota);
        return ResponseEntity.ok(savedCota);
    }

    @GetMapping
    @Operation(summary = "Listează toate cotele", description = "Returnează lista tuturor cotelor")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista cotelor returnată"),
        @ApiResponse(responseCode = "403", description = "Acces interzis (necesită rol ADMIN sau USER)")
    })
    public ResponseEntity<List<Cota>> getAllCote() {
        List<Cota> cote = cotaService.getAllCote();
        return ResponseEntity.ok(cote);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obține o cotă după ID", description = "Returnează detaliile unei cote specifice")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cotă găsită"),
        @ApiResponse(responseCode = "404", description = "Cota nu a fost găsită"),
        @ApiResponse(responseCode = "403", description = "Acces interzis (necesită rol ADMIN sau USER)")
    })
    public ResponseEntity<Cota> getCotaById(@PathVariable Long id) {
        return cotaService.getCotaById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/meci/{meciId}")
    @Operation(summary = "Obține cotele unui meci", description = "Returnează lista cotelor pentru un meci specific")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista cotelor returnată"),
        @ApiResponse(responseCode = "403", description = "Acces interzis (necesită rol ADMIN sau USER)")
    })
    public ResponseEntity<List<Cota>> getCoteByMeciId(@PathVariable Long meciId) {
        System.out.println("Cerere primită pentru URL: /cote/meci/" + meciId);
        System.out.println("Autentificare în CotaController: " + SecurityContextHolder.getContext().getAuthentication());
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            System.out.println("Utilizator autentificat: " + SecurityContextHolder.getContext().getAuthentication().getName());
            System.out.println("Autorități: " + SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        } else {
            System.out.println("Niciun utilizator autentificat!");
        }
        List<Cota> cote = cotaService.getCoteByMeciId(meciId);
        return ResponseEntity.ok(cote);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizează o cotă", description = "Actualizează detaliile unei cote existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cotă actualizată cu succes"),
        @ApiResponse(responseCode = "404", description = "Cota nu a fost găsită"),
        @ApiResponse(responseCode = "403", description = "Acces interzis (necesită rol ADMIN sau USER)")
    })
    public ResponseEntity<Cota> updateCota(@PathVariable Long id, @RequestBody Cota cota) {
        try {
            Cota updatedCota = cotaService.updateCota(id, cota);
            return ResponseEntity.ok(updatedCota);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Șterge o cotă", description = "Șterge o cotă existentă din sistem")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Cotă ștearsă cu succes"),
        @ApiResponse(responseCode = "404", description = "Cota nu a fost găsită"),
        @ApiResponse(responseCode = "403", description = "Acces interzis (necesită rol ADMIN sau USER)")
    })
    public ResponseEntity<Void> deleteCota(@PathVariable Long id) {
        try {
            cotaService.deleteCota(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}