package proiect.bet.sportbet.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import proiect.bet.sportbet.models.Cota;
import proiect.bet.sportbet.service.CotaService;

import java.util.List;

@RestController
@RequestMapping("/cote")
public class CotaController {
    private final CotaService cotaService;

    public CotaController(CotaService cotaService) {
        this.cotaService = cotaService;
    }

    @PostMapping
    public ResponseEntity<Cota> createCota(@RequestBody Cota cota) {
        Cota savedCota = cotaService.createCota(cota);
        return ResponseEntity.ok(savedCota);
    }

    @GetMapping
    public ResponseEntity<List<Cota>> getAllCote() {
        List<Cota> cote = cotaService.getAllCote();
        return ResponseEntity.ok(cote);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cota> getCotaById(@PathVariable Long id) {
        return cotaService.getCotaById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cota> updateCota(@PathVariable Long id, @RequestBody Cota cota) {
        try {
            Cota updatedCota = cotaService.updateCota(id, cota);
            return ResponseEntity.ok(updatedCota);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCota(@PathVariable Long id) {
        try {
            cotaService.deleteCota(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}