package proiect.bet.sportbet.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "invitatii_grup")
public class InvitatieGrup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_invitatie", nullable = false)
    private LocalDateTime dataInvitatie = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "grup_id", nullable = false)
    private GrupPrivat grup;

    @ManyToOne
    @JoinColumn(name = "utilizator_id", nullable = false)
    private Utilizator utilizator;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusInvitatie status = StatusInvitatie.PENDING;

    @Column(name = "acceptat", nullable = false) // Presupunem că aceasta este coloana problemă
    private Boolean acceptat; // Sau un alt tip, ex. LocalDateTime dacă reprezintă data acceptării

    public enum StatusInvitatie {
        PENDING, ACCEPTED, REJECTED
    }

    // Getteri și setteri
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDateTime getDataInvitatie() { return dataInvitatie; }
    public void setDataInvitatie(LocalDateTime dataInvitatie) { this.dataInvitatie = dataInvitatie; }
    public GrupPrivat getGrup() { return grup; }
    public void setGrup(GrupPrivat grup) { this.grup = grup; }
    public Utilizator getUtilizator() { return utilizator; }
    public void setUtilizator(Utilizator utilizator) { this.utilizator = utilizator; }
    public StatusInvitatie getStatus() { return status; }
    public void setStatus(StatusInvitatie status) { this.status = status; }
    public Boolean getAcceptat() { return acceptat; }
    public void setAcceptat(Boolean acceptat) { this.acceptat = acceptat; }

    // Equals și hashCode
   
}