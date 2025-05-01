package proiect.bet.sportbet.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import proiect.bet.sportbet.models.GrupPrivat;
import proiect.bet.sportbet.service.GrupPrivatService;

import java.util.List;

@RestController
@RequestMapping("/grupuri-private")
public class GrupPrivatController {
    private final GrupPrivatService grupPrivatService;

    public GrupPrivatController(GrupPrivatService grupPrivatService) {
        this.grupPrivatService = grupPrivatService;
    }

    @PostMapping
    public ResponseEntity<GrupPrivat> createGrupPrivat(@RequestBody GrupPrivat grupPrivat) {
        GrupPrivat savedGrupPrivat = grupPrivatService.createGrupPrivat(grupPrivat);
        return ResponseEntity.ok(savedGrupPrivat);
    }

    @GetMapping
    public ResponseEntity<List<GrupPrivat>> getAllGrupuriPrivate() {
        List<GrupPrivat> grupuriPrivate = grupPrivatService.getAllGrupuriPrivate();
        return ResponseEntity.ok(grupuriPrivate);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GrupPrivat> getGrupPrivatById(@PathVariable Long id) {
        return grupPrivatService.getGrupPrivatById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<GrupPrivat> updateGrupPrivat(@PathVariable Long id, @RequestBody GrupPrivat grupPrivat) {
        try {
            GrupPrivat updatedGrupPrivat = grupPrivatService.updateGrupPrivat(id, grupPrivat);
            return ResponseEntity.ok(updatedGrupPrivat);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGrupPrivat(@PathVariable Long id) {
        try {
            grupPrivatService.deleteGrupPrivat(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}