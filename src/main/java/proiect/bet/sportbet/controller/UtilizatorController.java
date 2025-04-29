package proiect.bet.sportbet.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import proiect.bet.sportbet.models.Utilizator;
import proiect.bet.sportbet.service.UtilizatorService;

import java.util.List;

@RestController
@RequestMapping("/utilizatori")
public class UtilizatorController {
    private final UtilizatorService utilizatorService;

    public UtilizatorController(UtilizatorService utilizatorService) {
        this.utilizatorService = utilizatorService;
    }

    // Create
    @PostMapping
    public ResponseEntity<Utilizator> createUtilizator(@RequestBody Utilizator utilizator) {
        Utilizator savedUtilizator = utilizatorService.createUtilizator(utilizator);
        return ResponseEntity.ok(savedUtilizator);
    }

    // Read (all)
    @GetMapping
    public ResponseEntity<List<Utilizator>> getAllUtilizatori() {
        List<Utilizator> utilizatori = utilizatorService.getAllUtilizatori();
        return ResponseEntity.ok(utilizatori);
    }

    // Read (by ID)
    @GetMapping("/{id}")
    public ResponseEntity<Utilizator> getUtilizatorById(@PathVariable Long id) {
        return utilizatorService.getUtilizatorById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<Utilizator> updateUtilizator(@PathVariable Long id, @RequestBody Utilizator utilizator) {
        try {
            Utilizator updatedUtilizator = utilizatorService.updateUtilizator(id, utilizator);
            return ResponseEntity.ok(updatedUtilizator);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUtilizator(@PathVariable Long id) {
        try {
            utilizatorService.deleteUtilizator(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}