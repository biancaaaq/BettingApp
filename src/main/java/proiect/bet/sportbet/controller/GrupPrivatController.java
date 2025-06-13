package proiect.bet.sportbet.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import proiect.bet.sportbet.models.GrupPrivat;
import proiect.bet.sportbet.models.InvitatieGrup;
import proiect.bet.sportbet.models.Bilet;
import proiect.bet.sportbet.service.GrupPrivatService;
import proiect.bet.sportbet.models.Mesaj;
import proiect.bet.sportbet.repository.MesajRepository;
import java.util.List;

@RestController
@RequestMapping("/grupuri-private")
@Tag(name = "Grupuri Private", description = "Operațiuni pentru gestionarea grupurilor private")
public class GrupPrivatController {
    private final GrupPrivatService grupPrivatService;

    public GrupPrivatController(GrupPrivatService grupPrivatService) {
        this.grupPrivatService = grupPrivatService;
    }

    @PostMapping
    @Operation(summary = "Adaugă un grup privat nou", description = "Creează un grup privat nou în sistem")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Grup privat adăugat cu succes"),
        @ApiResponse(responseCode = "403", description = "Acces interzis (necesită rol ADMIN sau USER)")
    })
    public ResponseEntity<GrupPrivat> createGrupPrivat(@RequestBody GrupPrivat grupPrivat, @RequestParam Double mizaComuna) {
        GrupPrivat savedGrupPrivat = grupPrivatService.createGrupPrivat(grupPrivat, mizaComuna);
        return ResponseEntity.ok(savedGrupPrivat);
    }

    @PostMapping("/{id}/invite")
    @Operation(summary = "Invită un utilizator în grup", description = "Creează o invitație pentru un utilizator în grup")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Utilizator invitat cu succes"),
        @ApiResponse(responseCode = "403", description = "Acces interzis (doar adminul poate invita)"),
        @ApiResponse(responseCode = "404", description = "Grupul sau utilizatorul nu a fost găsit")
    })
    public ResponseEntity<InvitatieGrup> inviteUser(@PathVariable Long id, @RequestParam String username) {
        InvitatieGrup invitatie = grupPrivatService.inviteUser(id, username);
        return ResponseEntity.ok(invitatie);
    }

    @PutMapping("/invitatii/{id}/accept")
    @Operation(summary = "Acceptă o invitație", description = "Acceptă o invitație la un grup privat")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Invitație acceptată cu succes"),
        @ApiResponse(responseCode = "403", description = "Acces interzis (doar utilizatorul invitat poate accepta)"),
        @ApiResponse(responseCode = "404", description = "Invitația nu a fost găsită")
    })
    public ResponseEntity<String> acceptInvitatie(@PathVariable Long id) {
        grupPrivatService.acceptInvitatie(id);
        return ResponseEntity.ok("Invitație acceptată cu succes");
    }

    @PutMapping("/invitatii/{id}/reject")
    @Operation(summary = "Refuză o invitație", description = "Refuză o invitație la un grup privat")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Invitație refuzată cu succes"),
        @ApiResponse(responseCode = "403", description = "Acces interzis (doar utilizatorul invitat poate refuza)"),
        @ApiResponse(responseCode = "404", description = "Invitația nu a fost găsită")
    })
    public ResponseEntity<String> rejectInvitatie(@PathVariable Long id) {
        grupPrivatService.rejectInvitatie(id);
        return ResponseEntity.ok("Invitație refuzată cu succes");
    }

    @PostMapping("/{id}/bilet")
    @Operation(summary = "Plasează un bilet în grup", description = "Plasează un bilet în contextul unui grup privat")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Bilet plasat cu succes"),
        @ApiResponse(responseCode = "403", description = "Acces interzis (doar membrii pot plasa bilete)"),
        @ApiResponse(responseCode = "400", description = "Miza invalidă sau bilet existent")
    })
    public ResponseEntity<Bilet> placeBilet(@PathVariable Long id, @RequestBody Bilet bilet) {
        Bilet savedBilet = grupPrivatService.placeBiletInGrup(id, bilet);
        return ResponseEntity.ok(savedBilet);
    }

    @GetMapping
    @Operation(summary = "Listează toate grupurile private", description = "Returnează lista tuturor grupurilor private")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista grupurilor private returnată"),
        @ApiResponse(responseCode = "403", description = "Acces interzis (necesită rol ADMIN sau USER)")
    })
    public ResponseEntity<List<GrupPrivat>> getAllGrupuriPrivate() {
        List<GrupPrivat> grupuriPrivate = grupPrivatService.getAllGrupuriPrivate();
        return ResponseEntity.ok(grupuriPrivate);
    }

    @GetMapping("/mele")
    @Operation(summary = "Listează grupurile utilizatorului", description = "Returnează grupurile în care utilizatorul este membru")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista grupurilor returnată"),
        @ApiResponse(responseCode = "403", description = "Acces interzis (necesită rol USER sau ADMIN)")
    })
    public ResponseEntity<List<GrupPrivat>> getGrupuriUtilizator() {
        List<GrupPrivat> grupuri = grupPrivatService.getGrupuriByUtilizator();
        return ResponseEntity.ok(grupuri);
    }

    @GetMapping("/invitatii")
    @Operation(summary = "Listează invitațiile utilizatorului", description = "Returnează invitațiile primite de utilizator")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista invitațiilor returnată"),
        @ApiResponse(responseCode = "403", description = "Acces interzis (necesită rol USER sau ADMIN)")
    })
    public ResponseEntity<List<InvitatieGrup>> getInvitatiiUtilizator() {
        List<InvitatieGrup> invitatii = grupPrivatService.getInvitatiiByUtilizator();
        return ResponseEntity.ok(invitatii);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obține un grup privat după ID", description = "Returnează detaliile unui grup privat specific")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Grup privat găsit"),
        @ApiResponse(responseCode = "404", description = "Grupul privat nu a fost găsit"),
        @ApiResponse(responseCode = "403", description = "Acces interzis (necesită rol ADMIN sau USER)")
    })
    public ResponseEntity<GrupPrivat> getGrupPrivatById(@PathVariable Long id) {
        return grupPrivatService.getGrupPrivatById(id)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizează un grup privat", description = "Actualizează detaliile unui grup privat existent")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Grup privat actualizat cu succes"),
        @ApiResponse(responseCode = "404", description = "Grupul privat nu a fost găsit"),
        @ApiResponse(responseCode = "403", description = "Acces interzis (necesită rol ADMIN sau USER)")
    })
    public ResponseEntity<GrupPrivat> updateGrupPrivat(@PathVariable Long id, @RequestBody GrupPrivat grupPrivat) {
        try {
            GrupPrivat updatedGrupPrivat = grupPrivatService.updateGrupPrivat(id, grupPrivat);
            return ResponseEntity.ok(updatedGrupPrivat);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Șterge un grup privat", description = "Șterge un grup privat existent din sistem")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Grup privat șters cu succes"),
        @ApiResponse(responseCode = "404", description = "Grupul privat nu a fost găsit"),
        @ApiResponse(responseCode = "403", description = "Acces interzis (necesită rol ADMIN sau USER)")
    })
    public ResponseEntity<Void> deleteGrupPrivat(@PathVariable Long id) {
        try {
            grupPrivatService.deleteGrupPrivat(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @Autowired
    private MesajRepository mesajRepository;

    @GetMapping("/{id}/mesaje")
    public ResponseEntity<List<Mesaj>> getMesaje(@PathVariable Long id) {
        List<Mesaj> mesaje = mesajRepository.findByGrupIdOrderByTimestampAsc(id);
        System.out.println("Mesaje returnate pentru grup " + id + ": " + mesaje);
        return ResponseEntity.ok(mesaje);
    }
}