package proiect.bet.sportbet.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "grup_membri")
public class GrupMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "grup_id", nullable = false)
    @JsonBackReference
    private GrupPrivat grup;

    @ManyToOne
    @JoinColumn(name = "utilizator_id", nullable = false)
    private Utilizator utilizator;

    // Getteri și setteri
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public GrupPrivat getGrup() { return grup; }
    public void setGrup(GrupPrivat grup) { this.grup = grup; }
    public Utilizator getUtilizator() { return utilizator; }
    public void setUtilizator(Utilizator utilizator) { this.utilizator = utilizator; }

    // Equals și hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GrupMember that = (GrupMember) o;
        return Objects.equals(grup, that.grup) &&
               Objects.equals(utilizator, that.utilizator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(grup, utilizator);
    }

    // toString
    @Override
    public String toString() {
        return "GrupMember{" +
               "id=" + id +
               ", utilizator=" + utilizator.getNumeUtilizator() +
               '}';
    }
}