package proiect.bet.sportbet.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "grupuri_private")
public class GrupPrivat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nume;

    @ManyToOne
    @JoinColumn(name = "id_admin", nullable = false)
    private Utilizator admin;

    @Column(name = "link_invitatie", nullable = false, length = 255)
    private String linkInvitatie;

    @Column(name = "data_creare", nullable = false)
    private LocalDateTime dataCreare = LocalDateTime.now();

    // Getteri și setteri
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNume() { return nume; }
    public void setNume(String nume) { this.nume = nume; }
    public Utilizator getAdmin() { return admin; }
    public void setAdmin(Utilizator admin) { this.admin = admin; }
    public String getLinkInvitatie() { return linkInvitatie; }
    public void setLinkInvitatie(String linkInvitatie) { this.linkInvitatie = linkInvitatie; }
    public LocalDateTime getDataCreare() { return dataCreare; }
    public void setDataCreare(LocalDateTime dataCreare) { this.dataCreare = dataCreare; }
}