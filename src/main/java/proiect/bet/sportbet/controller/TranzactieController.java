package proiect.bet.sportbet.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import proiect.bet.sportbet.models.Tranzactie;
import proiect.bet.sportbet.service.TranzactieService;

import java.util.List;

@RestController
@RequestMapping("/tranzactii")
public class TranzactieController {
    private final TranzactieService tranzactieService;

    public TranzactieController(TranzactieService tranzactieService) {
        this.tranzactieService = tranzactieService;
    }

    @PostMapping
    public ResponseEntity<Tranzactie> createTranzactie(@RequestBody Tranzactie tranzactie) {
        Tranzactie savedTranzactie = tranzactieService.createTranzactie(tranzactie);
        return ResponseEntity.ok(savedTranzactie);
    }

    @GetMapping
    public ResponseEntity<List<Tranzactie>> getAllTranzactii() {
        List<Tranzactie> tranzactii = tranzactieService.getAllTranzactii();
        return ResponseEntity.ok(tranzactii);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tranzactie> getTranzactieById(@PathVariable Long id) {
        return tranzactieService.getTranzactieById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tranzactie> updateTranzactie(@PathVariable Long id, @RequestBody Tranzactie tranzactie) {
        try {
            Tranzactie updatedTranzactie = tranzactieService.updateTranzactie(id, tranzactie);
            return ResponseEntity.ok(updatedTranzactie);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTranzactie(@PathVariable Long id) {
        try {
            tranzactieService.deleteTranzactie(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}