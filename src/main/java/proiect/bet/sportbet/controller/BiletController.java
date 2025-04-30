package proiect.bet.sportbet.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import proiect.bet.sportbet.models.Bilet;
import proiect.bet.sportbet.service.BiletService;

import java.util.List;

@RestController
@RequestMapping("/bilete")
public class BiletController {
    private final BiletService biletService;

    public BiletController(BiletService biletService) {
        this.biletService = biletService;
    }

    @PostMapping
    public ResponseEntity<Bilet> createBilet(@RequestBody Bilet bilet) {
        Bilet savedBilet = biletService.createBilet(bilet);
        return ResponseEntity.ok(savedBilet);
    }

    @GetMapping
    public ResponseEntity<List<Bilet>> getAllBilete() {
        List<Bilet> bilete = biletService.getAllBilete();
        return ResponseEntity.ok(bilete);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bilet> getBiletById(@PathVariable Long id) {
        return biletService.getBiletById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Bilet> updateBilet(@PathVariable Long id, @RequestBody Bilet bilet) {
        try {
            Bilet updatedBilet = biletService.updateBilet(id, bilet);
            return ResponseEntity.ok(updatedBilet);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBilet(@PathVariable Long id) {
        try {
            biletService.deleteBilet(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}