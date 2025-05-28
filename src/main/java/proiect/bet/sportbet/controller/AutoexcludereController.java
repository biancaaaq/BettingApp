package proiect.bet.sportbet.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import proiect.bet.sportbet.models.CerereAutoexcludere;
import proiect.bet.sportbet.models.Utilizator;
import proiect.bet.sportbet.repository.CerereAutoexcludereRepository;
import proiect.bet.sportbet.repository.UtilizatorRepository;

import java.util.List; // Importăm List
import java.time.LocalDateTime; // Importăm LocalDateTime

@RestController
@RequestMapping("/autoexcludere")
@Tag(name = "Autoexcludere", description = "Operațiuni pentru auto-excluderea utilizatorilor")
public class AutoexcludereController {
    private final UtilizatorRepository utilizatorRepository;
    private final CerereAutoexcludereRepository cerereAutoexcludereRepository;

    public AutoexcludereController(UtilizatorRepository utilizatorRepository, CerereAutoexcludereRepository cerereAutoexcludereRepository) {
        this.utilizatorRepository = utilizatorRepository;
        this.cerereAutoexcludereRepository = cerereAutoexcludereRepository;
    }

    @PostMapping
    @Operation(summary = "Creează o cerere de auto-excludere", description = "Creează o cerere de auto-excludere pentru utilizatorul autentificat")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cerere de auto-excludere creată cu succes"),
        @ApiResponse(responseCode = "403", description = "Acces interzis (necesită rol USER sau ADMIN)"),
        @ApiResponse(responseCode = "404", description = "Utilizatorul nu a fost găsit")
    })
    public ResponseEntity<String> autoexclude() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Utilizator utilizator = utilizatorRepository.findByNumeUtilizator(username)
                .orElseThrow(() -> new RuntimeException("Utilizatorul nu a fost găsit"));

        // Creează o cerere de auto-excludere
        CerereAutoexcludere cerere = new CerereAutoexcludere();
        cerere.setUtilizator(utilizator);
        cerere.setCerere(LocalDateTime.now());
        cerere.setAprobat(false); // Cererea va fi aprobată de un admin
        cerereAutoexcludereRepository.save(cerere);

        return ResponseEntity.ok("Cerere de auto-excludere creată cu succes. Așteaptă aprobarea unui admin.");
    }

    @GetMapping("/cereri")
    @Operation(summary = "Listează cererile de auto-excludere neaprobate", description = "Returnează lista cererilor de auto-excludere neaprobate (doar pentru ADMIN)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista cererilor returnată"),
        @ApiResponse(responseCode = "403", description = "Acces interzis (necesită rol ADMIN)")
    })
    public ResponseEntity<List<CerereAutoexcludere>> getCereriNeaprobate() {
        List<CerereAutoexcludere> cereri = cerereAutoexcludereRepository.findByAprobatFalse();
        return ResponseEntity.ok(cereri);
    }

    @PutMapping("/aproba/{id}")
    @Operation(summary = "Aprobă o cerere de auto-excludere", description = "Aprobă o cerere de auto-excludere și dezactivează utilizatorul (doar pentru ADMIN)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cerere aprobată și utilizator dezactivat"),
        @ApiResponse(responseCode = "403", description = "Acces interzis (necesită rol ADMIN)"),
        @ApiResponse(responseCode = "404", description = "Cererea nu a fost găsită")
    })
    public ResponseEntity<String> aprobaCerere(@PathVariable Long id) {
        CerereAutoexcludere cerere = cerereAutoexcludereRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cererea de auto-excludere nu a fost găsită"));

        cerere.setAprobat(true);
        cerereAutoexcludereRepository.save(cerere);

        Utilizator utilizator = cerere.getUtilizator();
        utilizator.setActiv(false);
        utilizatorRepository.save(utilizator);

        return ResponseEntity.ok("Cerere aprobată. Utilizatorul a fost dezactivat.");
    }
}