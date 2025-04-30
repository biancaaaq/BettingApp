package proiect.bet.sportbet.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import proiect.bet.sportbet.models.RolUtilizator;
import proiect.bet.sportbet.service.RolUtilizatorService;

import java.util.List;

@RestController
@RequestMapping("/rol-utilizatori")
public class RolUtilizatorController {
    private final RolUtilizatorService rolUtilizatorService;

    public RolUtilizatorController(RolUtilizatorService rolUtilizatorService) {
        this.rolUtilizatorService = rolUtilizatorService;
    }

    @PostMapping
    public ResponseEntity<RolUtilizator> createRolUtilizator(@RequestBody RolUtilizator rolUtilizator) {
        RolUtilizator savedRolUtilizator = rolUtilizatorService.createRolUtilizator(rolUtilizator);
        return ResponseEntity.ok(savedRolUtilizator);
    }

    @GetMapping
    public ResponseEntity<List<RolUtilizator>> getAllRolUtilizatori() {
        List<RolUtilizator> rolUtilizatori = rolUtilizatorService.getAllRolUtilizatori();
        return ResponseEntity.ok(rolUtilizatori);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RolUtilizator> getRolUtilizatorById(@PathVariable Long id) {
        return rolUtilizatorService.getRolUtilizatorById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<RolUtilizator> updateRolUtilizator(@PathVariable Long id, @RequestBody RolUtilizator rolUtilizator) {
        try {
            RolUtilizator updatedRolUtilizator = rolUtilizatorService.updateRolUtilizator(id, rolUtilizator);
            return ResponseEntity.ok(updatedRolUtilizator);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRolUtilizator(@PathVariable Long id) {
        try {
            rolUtilizatorService.deleteRolUtilizator(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}