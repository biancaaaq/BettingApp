package proiect.bet.sportbet.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import proiect.bet.sportbet.models.Balanta;
import proiect.bet.sportbet.service.BalantaService;

import java.util.List;

@RestController
@RequestMapping("/balanta")
public class BalantaController {
    private final BalantaService balantaService;

    public BalantaController(BalantaService balantaService) {
        this.balantaService = balantaService;
    }

    @PostMapping
    public ResponseEntity<Balanta> createBalanta(@RequestBody Balanta balanta) {
        Balanta savedBalanta = balantaService.createBalanta(balanta);
        return ResponseEntity.ok(savedBalanta);
    }

    @GetMapping
    public ResponseEntity<List<Balanta>> getAllBalante() {
        List<Balanta> balante = balantaService.getAllBalante();
        return ResponseEntity.ok(balante);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Balanta> getBalantaById(@PathVariable Long id) {
        return balantaService.getBalantaById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Balanta> updateBalanta(@PathVariable Long id, @RequestBody Balanta balanta) {
        try {
            Balanta updatedBalanta = balantaService.updateBalanta(id, balanta);
            return ResponseEntity.ok(updatedBalanta);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBalanta(@PathVariable Long id) {
        try {
            balantaService.deleteBalanta(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}