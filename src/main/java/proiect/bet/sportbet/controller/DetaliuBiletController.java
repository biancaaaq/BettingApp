package proiect.bet.sportbet.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import proiect.bet.sportbet.models.DetaliuBilet;
import proiect.bet.sportbet.service.DetaliuBiletService;

import java.util.List;

@RestController
@RequestMapping("/detalii-bilete")
public class DetaliuBiletController {
    private final DetaliuBiletService detaliuBiletService;

    public DetaliuBiletController(DetaliuBiletService detaliuBiletService) {
        this.detaliuBiletService = detaliuBiletService;
    }

    @PostMapping
    public ResponseEntity<DetaliuBilet> createDetaliuBilet(@RequestBody DetaliuBilet detaliuBilet) {
        DetaliuBilet savedDetaliuBilet = detaliuBiletService.createDetaliuBilet(detaliuBilet);
        return ResponseEntity.ok(savedDetaliuBilet);
    }

    @GetMapping
    public ResponseEntity<List<DetaliuBilet>> getAllDetaliiBilete() {
        List<DetaliuBilet> detaliiBilete = detaliuBiletService.getAllDetaliiBilete();
        return ResponseEntity.ok(detaliiBilete);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetaliuBilet> getDetaliuBiletById(@PathVariable Long id) {
        return detaliuBiletService.getDetaliuBiletById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetaliuBilet> updateDetaliuBilet(@PathVariable Long id, @RequestBody DetaliuBilet detaliuBilet) {
        try {
            DetaliuBilet updatedDetaliuBilet = detaliuBiletService.updateDetaliuBilet(id, detaliuBilet);
            return ResponseEntity.ok(updatedDetaliuBilet);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDetaliuBilet(@PathVariable Long id) {
        try {
            detaliuBiletService.deleteDetaliuBilet(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}