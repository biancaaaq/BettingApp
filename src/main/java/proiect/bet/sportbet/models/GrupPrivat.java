package proiect.bet.sportbet.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Column(name = "miza_comuna", nullable = false)
    private Double mizaComuna;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusGrup status = StatusGrup.IN_ASTEPTARE;

    @OneToMany(mappedBy = "grup", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<GrupMember> membri = new ArrayList<>();

    @OneToMany(mappedBy = "grup")
    @JsonManagedReference
    private List<Bilet> bilete = new ArrayList<>();

    public enum StatusGrup {
        IN_ASTEPTARE, ACTIV, FINALIZAT
    }

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
    public Double getMizaComuna() { return mizaComuna; }
    public void setMizaComuna(Double mizaComuna) { this.mizaComuna = mizaComuna; }
    public StatusGrup getStatus() { return status; }
    public void setStatus(StatusGrup status) { this.status = status; }
    public List<GrupMember> getMembri() { return membri; }
    public void setMembri(List<GrupMember> membri) { this.membri = membri; }
    public List<Bilet> getBilete() { return bilete; }
    public void setBilete(List<Bilet> bilete) { this.bilete = bilete; }

    // Equals și hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GrupPrivat that = (GrupPrivat) o;
        return Objects.equals(id, that.id) &&
               Objects.equals(nume, that.nume) &&
               Objects.equals(admin, that.admin) &&
               Objects.equals(linkInvitatie, that.linkInvitatie) &&
               Objects.equals(dataCreare, that.dataCreare) &&
               Objects.equals(mizaComuna, that.mizaComuna) &&
               status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nume, admin, linkInvitatie, dataCreare, mizaComuna, status);
    }

    // toString
    @Override
    public String toString() {
        return "GrupPrivat{" +
               "id=" + id +
               ", nume='" + nume + '\'' +
               ", admin=" + admin +
               ", linkInvitatie='" + linkInvitatie + '\'' +
               ", dataCreare=" + dataCreare +
               ", mizaComuna=" + mizaComuna +
               ", status=" + status +
               ", membri=" + membri.size() +
               '}';
    }
}