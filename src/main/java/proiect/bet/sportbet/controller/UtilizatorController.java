package proiect.bet.sportbet.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import proiect.bet.sportbet.models.Utilizator;
import proiect.bet.sportbet.repository.UtilizatorRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/utilizatori")
@Tag(name = "Utilizatori", description = "Operațiuni pentru gestionarea utilizatorilor")
public class UtilizatorController {

    private final UtilizatorRepository utilizatorRepository;

    public UtilizatorController(UtilizatorRepository utilizatorRepository) {
        this.utilizatorRepository = utilizatorRepository;
    }

    @GetMapping
    @Operation(summary = "Listează toți utilizatorii", description = "Returnează lista tuturor utilizatorilor")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista utilizatorilor returnată"),
        @ApiResponse(responseCode = "403", description = "Acces interzis (necesită rol ADMIN)")
    })
    public ResponseEntity<List<Utilizator>> getAllUtilizatori() {
        List<Utilizator> utilizatori = utilizatorRepository.findAll();
        return ResponseEntity.ok(utilizatori);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obține un utilizator după ID", description = "Returnează detaliile unui utilizator specific")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Utilizator găsit"),
        @ApiResponse(responseCode = "404", description = "Utilizatorul nu a fost găsit"),
        @ApiResponse(responseCode = "403", description = "Acces interzis (necesită rol ADMIN)")
    })
    public ResponseEntity<Utilizator> getUtilizatorById(@PathVariable Long id) {
        return utilizatorRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizează un utilizator", description = "Actualizează detaliile unui utilizator existent")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Utilizator actualizat cu succes"),
        @ApiResponse(responseCode = "404", description = "Utilizatorul nu a fost găsit"),
        @ApiResponse(responseCode = "403", description = "Acces interzis (necesită rol ADMIN)")
    })
    public ResponseEntity<Utilizator> updateUtilizator(@PathVariable Long id, @RequestBody Utilizator utilizator) {
        if (!utilizatorRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        utilizator.setId(id);
        Utilizator updatedUtilizator = utilizatorRepository.save(utilizator);
        return ResponseEntity.ok(updatedUtilizator);
    }

    @PutMapping("/activare/{id}")
    @Operation(summary = "Reactivează un utilizator", description = "Reactivează un cont de utilizator dezactivat")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Utilizator reactivat cu succes"),
        @ApiResponse(responseCode = "404", description = "Utilizatorul nu a fost găsit")
    })
    public ResponseEntity<String> reactiveazaUtilizator(@PathVariable Long id) {
        Optional<Utilizator> utilizatorOpt = utilizatorRepository.findById(id);
        if (utilizatorOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Utilizator utilizator = utilizatorOpt.get();
        utilizator.setActiv(true);
        utilizatorRepository.save(utilizator);
        return ResponseEntity.ok("Utilizator reactivat cu succes!");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Șterge un utilizator", description = "Șterge un utilizator din sistem")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Utilizator șters cu succes"),
        @ApiResponse(responseCode = "404", description = "Utilizatorul nu a fost găsit"),
        @ApiResponse(responseCode = "403", description = "Acces interzis (necesită rol ADMIN)")
    })
    public ResponseEntity<Void> deleteUtilizator(@PathVariable Long id) {
        if (!utilizatorRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        utilizatorRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}