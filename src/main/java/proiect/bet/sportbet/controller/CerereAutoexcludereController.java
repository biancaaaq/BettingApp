package proiect.bet.sportbet.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import proiect.bet.sportbet.models.CerereAutoexcludere;
import proiect.bet.sportbet.service.CerereAutoexcludereService;

import java.util.List;

@RestController
@RequestMapping("/cereri-autoexcludere")
public class CerereAutoexcludereController {
    private final CerereAutoexcludereService cerereAutoexcludereService;

    public CerereAutoexcludereController(CerereAutoexcludereService cerereAutoexcludereService) {
        this.cerereAutoexcludereService = cerereAutoexcludereService;
    }

    @PostMapping
    public ResponseEntity<CerereAutoexcludere> createCerereAutoexcludere(@RequestBody CerereAutoexcludere cerereAutoexcludere) {
        CerereAutoexcludere savedCerereAutoexcludere = cerereAutoexcludereService.createCerereAutoexcludere(cerereAutoexcludere);
        return ResponseEntity.ok(savedCerereAutoexcludere);
    }

    @GetMapping
    public ResponseEntity<List<CerereAutoexcludere>> getAllCereriAutoexcludere() {
        List<CerereAutoexcludere> cereriAutoexcludere = cerereAutoexcludereService.getAllCereriAutoexcludere();
        return ResponseEntity.ok(cereriAutoexcludere);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CerereAutoexcludere> getCerereAutoexcludereById(@PathVariable Long id) {
        return cerereAutoexcludereService.getCerereAutoexcludereById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CerereAutoexcludere> updateCerereAutoexcludere(@PathVariable Long id, @RequestBody CerereAutoexcludere cerereAutoexcludere) {
        try {
            CerereAutoexcludere updatedCerereAutoexcludere = cerereAutoexcludereService.updateCerereAutoexcludere(id, cerereAutoexcludere);
            return ResponseEntity.ok(updatedCerereAutoexcludere);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCerereAutoexcludere(@PathVariable Long id) {
        try {
            cerereAutoexcludereService.deleteCerereAutoexcludere(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}