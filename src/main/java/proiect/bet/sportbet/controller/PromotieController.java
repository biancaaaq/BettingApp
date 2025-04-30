package proiect.bet.sportbet.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import proiect.bet.sportbet.models.Promotie;
import proiect.bet.sportbet.service.PromotieService;

import java.util.List;

@RestController
@RequestMapping("/promotii")
public class PromotieController {
    private final PromotieService promotieService;

    public PromotieController(PromotieService promotieService) {
        this.promotieService = promotieService;
    }

    @PostMapping
    public ResponseEntity<Promotie> createPromotie(@RequestBody Promotie promotie) {
        Promotie savedPromotie = promotieService.createPromotie(promotie);
        return ResponseEntity.ok(savedPromotie);
    }

    @GetMapping
    public ResponseEntity<List<Promotie>> getAllPromotii() {
        List<Promotie> promotii = promotieService.getAllPromotii();
        return ResponseEntity.ok(promotii);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Promotie> getPromotieById(@PathVariable Long id) {
        return promotieService.getPromotieById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Promotie> updatePromotie(@PathVariable Long id, @RequestBody Promotie promotie) {
        try {
            Promotie updatedPromotie = promotieService.updatePromotie(id, promotie);
            return ResponseEntity.ok(updatedPromotie);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePromotie(@PathVariable Long id) {
        try {
            promotieService.deletePromotie(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}