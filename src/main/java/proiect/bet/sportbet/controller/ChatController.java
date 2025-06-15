package proiect.bet.sportbet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;
import proiect.bet.sportbet.models.GrupPrivat;
import proiect.bet.sportbet.models.Mesaj;
import proiect.bet.sportbet.models.MesajDto;
import proiect.bet.sportbet.models.Utilizator;
import proiect.bet.sportbet.repository.GrupPrivatRepository;
import proiect.bet.sportbet.repository.MesajRepository;
import proiect.bet.sportbet.repository.UtilizatorRepository;

import java.time.LocalDateTime;

@RestController
public class ChatController {

    @Autowired
    private MesajRepository mesajRepository;

    @Autowired
    private GrupPrivatRepository grupPrivatRepository;

    @Autowired
    private UtilizatorRepository utilizatorRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/grup/{grupId}/mesaj")
    @SendTo("/topic/grup/{grupId}")
    public Mesaj handleMesaj(@DestinationVariable Long grupId, MesajDto mesajDto, Authentication authentication) {
        try {
            System.out.println("Mesaj primit pentru grupul " + grupId + ": " + (mesajDto.getContinut() != null ? mesajDto.getContinut() : "null"));

            // Obține utilizatorul autentificat din contextul de securitate
            String username = authentication != null ? authentication.getName() : "robert";
            System.out.println("Utilizator autentificat: " + username);
            
            // Căutăm utilizatorul în baza de date pe baza numelui utilizatorului
            Utilizator utilizator = utilizatorRepository.findByNumeUtilizator(username)
                    .orElseThrow(() -> new RuntimeException("Utilizator nu a fost găsit: " + username));
            System.out.println("Utilizator găsit: " + utilizator.getNumeUtilizator() + " cu ID: " + utilizator.getId());

            // Căutăm grupul în baza de date pe baza ID-ului
            GrupPrivat grup = grupPrivatRepository.findById(grupId)
                    .orElseThrow(() -> new RuntimeException("Grup nu a fost găsit"));

            // Creăm un nou mesaj
            Mesaj mesaj = new Mesaj();
            mesaj.setContinut(mesajDto.getContinut());
            mesaj.setGrup(grup);
            mesaj.setUtilizator(utilizator); // Setează utilizatorul real
            mesaj.setTimestamp(LocalDateTime.now());

            // Salvăm mesajul în baza de date
            Mesaj savedMesaj = mesajRepository.save(mesaj);
            System.out.println("Mesaj salvat: " + savedMesaj);
            return savedMesaj; // Returnează mesajul cu utilizatorul real
        } catch (Exception e) {
            System.err.println("Eroare la procesarea mesajului: " + e.getMessage());
            throw new RuntimeException("Eroare la procesarea mesajului: " + e.getMessage());
        }
    }
}
