package proiect.bet.sportbet.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "mesaje")
public class Mesaj {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "grup_id", nullable = false)
    private GrupPrivat grup;

    @ManyToOne
    @JoinColumn(name = "utilizator_id", nullable = false)
    @JsonProperty("utilizator")
    private Utilizator utilizator;

    @Column(nullable = false, length = 1000)
    private String continut;

    @Column(nullable = false)
    private LocalDateTime timestamp = LocalDateTime.now();

    // Constructori
    public Mesaj() {
    }

    public Mesaj(GrupPrivat grup, Utilizator utilizator, String continut) {
        this.grup = grup;
        this.utilizator = utilizator;
        this.continut = continut;
    }

    // Getteri și setteri
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public GrupPrivat getGrup() { return grup; }
    public void setGrup(GrupPrivat grup) { this.grup = grup; }
    public Utilizator getUtilizator() { return utilizator; }
    public void setUtilizator(Utilizator utilizator) { this.utilizator = utilizator; }
    public String getContinut() { return continut; }
    public void setContinut(String continut) { this.continut = continut; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    // Equals și hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mesaj mesaj = (Mesaj) o;
        return Objects.equals(id, mesaj.id) &&
               Objects.equals(grup, mesaj.grup) &&
               Objects.equals(utilizator, mesaj.utilizator) &&
               Objects.equals(continut, mesaj.continut) &&
               Objects.equals(timestamp, mesaj.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, grup, utilizator, continut, timestamp);
    }

    // toString
    @Override
    public String toString() {
        return "Mesaj{" +
               "id=" + id +
               ", grup=" + grup.getNume() +
               ", utilizator=" + utilizator.getNumeUtilizator() +
               ", continut='" + continut + '\'' +
               ", timestamp=" + timestamp +
               '}';
    }
}