package proiect.bet.sportbet.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import proiect.bet.sportbet.models.Meci;
import proiect.bet.sportbet.service.MeciService;

import java.util.List;

@RestController
@RequestMapping("/meciuri")
public class MeciController {
    private final MeciService meciService;

    public MeciController(MeciService meciService) {
        this.meciService = meciService;
    }

    @PostMapping
    public ResponseEntity<Meci> createMeci(@RequestBody Meci meci) {
        Meci savedMeci = meciService.createMeci(meci);
        return ResponseEntity.ok(savedMeci);
    }

    @GetMapping
    public ResponseEntity<List<Meci>> getAllMeciuri() {
        List<Meci> meciuri = meciService.getAllMeciuri();
        return ResponseEntity.ok(meciuri);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Meci> getMeciById(@PathVariable Long id) {
        return meciService.getMeciById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Meci> updateMeci(@PathVariable Long id, @RequestBody Meci meci) {
        try {
            Meci updatedMeci = meciService.updateMeci(id, meci);
            return ResponseEntity.ok(updatedMeci);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMeci(@PathVariable Long id) {
        try {
            meciService.deleteMeci(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}