package proiect.bet.sportbet.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import proiect.bet.sportbet.models.Meci;
import proiect.bet.sportbet.service.ApiFootballImporterService;
import proiect.bet.sportbet.service.MeciService;

import java.util.List;

@RestController
@RequestMapping("/meciuri")
@Tag(name = "Meciuri", description = "Operațiuni pentru gestionarea meciurilor")
public class MeciController {
    private final MeciService meciService;
    private final ApiFootballImporterService importerService;

    public MeciController(MeciService meciService, ApiFootballImporterService importerService) {
        this.meciService = meciService;
        this.importerService = importerService;
}
     

    @GetMapping("/importa-api")
    public ResponseEntity<String> importaMeciuriDinApi() {
        importerService.importMeciuri();
        return ResponseEntity.ok("Importul a fost realizat cu succes!");
}


    @PostMapping
    @Operation(summary = "Adaugă un meci nou", description = "Creează un meci nou în sistem")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Meci adăugat cu succes"),
        @ApiResponse(responseCode = "403", description = "Acces interzis (necesită rol ADMIN sau USER)")
    })
    public ResponseEntity<Meci> createMeci(@RequestBody Meci meci) {
        Meci savedMeci = meciService.createMeci(meci);
        return ResponseEntity.ok(savedMeci);
    }

    @GetMapping
    @Operation(summary = "Listează toate meciurile", description = "Returnează lista tuturor meciurilor")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista meciurilor returnată"),
        @ApiResponse(responseCode = "403", description = "Acces interzis (necesită rol ADMIN sau USER)")
    })
    public ResponseEntity<List<Meci>> getAllMeciuri() {
        List<Meci> meciuri = meciService.getAllMeciuri();
        return ResponseEntity.ok(meciuri);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obține un meci după ID", description = "Returnează detaliile unui meci specific")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Meci găsit"),
        @ApiResponse(responseCode = "404", description = "Meciul nu a fost găsit"),
        @ApiResponse(responseCode = "403", description = "Acces interzis (necesită rol ADMIN sau USER)")
    })
    public ResponseEntity<Meci> getMeciById(@PathVariable Long id) {
        return meciService.getMeciById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizează un meci", description = "Actualizează detaliile unui meci existent")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Meci actualizat cu succes"),
        @ApiResponse(responseCode = "404", description = "Meciul nu a fost găsit"),
        @ApiResponse(responseCode = "403", description = "Acces interzis (necesită rol ADMIN sau USER)")
    })
    public ResponseEntity<Meci> updateMeci(@PathVariable Long id, @RequestBody Meci meci) {
        try {
            Meci updatedMeci = meciService.updateMeci(id, meci);
            return ResponseEntity.ok(updatedMeci);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Șterge un meci", description = "Șterge un meci existent din sistem")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Meci șters cu succes"),
        @ApiResponse(responseCode = "404", description = "Meciul nu a fost găsit"),
        @ApiResponse(responseCode = "403", description = "Acces interzis (necesită rol ADMIN sau USER)")
    })
    public ResponseEntity<Void> deleteMeci(@PathVariable Long id) {
        try {
            meciService.deleteMeci(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}