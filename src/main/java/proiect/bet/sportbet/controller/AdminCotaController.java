package proiect.bet.sportbet.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import proiect.bet.sportbet.service.CotaService;

@RestController
@RequestMapping("/admin/cote")
public class AdminCotaController {

    private final CotaService cotaService;

    public AdminCotaController(CotaService cotaService) {
        this.cotaService = cotaService;
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> actualizeazaStatusCota(
            @PathVariable Long id,
            @RequestParam String statusNou) {
        try {
            cotaService.actualizeazaStatusCota(id, statusNou);
            return ResponseEntity.ok("Statusul cotei a fost actualizat cu succes.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Eroare: " + e.getMessage());
        }
    }
}
