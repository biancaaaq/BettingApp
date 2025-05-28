package proiect.bet.sportbet.models;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "cote")
public class Cota {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_meci", nullable = false)
    private Meci meci;

    @Column(nullable = false)
    private String descriere;

    @Column(nullable = false)
    private Double valoare;

    @Column(nullable = false)
    private Boolean blocat;

    // Constructori
    public Cota() {
    }

    public Cota(Meci meci, String descriere, Double valoare, Boolean blocat) {
        this.meci = meci;
        this.descriere = descriere;
        this.valoare = valoare;
        this.blocat = blocat;
    }

    // Getteri și setteri
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Meci getMeci() {
        return meci;
    }

    public void setMeci(Meci meci) {
        this.meci = meci;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public Double getValoare() {
        return valoare;
    }

    public void setValoare(Double valoare) {
        this.valoare = valoare;
    }

    public Boolean getBlocat() {
        return blocat;
    }

    public void setBlocat(Boolean blocat) {
        this.blocat = blocat;
    }

    // Equals și hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cota cota = (Cota) o;
        return Objects.equals(id, cota.id) &&
               Objects.equals(meci, cota.meci) &&
               Objects.equals(descriere, cota.descriere) &&
               Objects.equals(valoare, cota.valoare) &&
               Objects.equals(blocat, cota.blocat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, meci, descriere, valoare, blocat);
    }

    // toString
    @Override
    public String toString() {
        return "Cota{" +
               "id=" + id +
               ", meci=" + (meci != null ? meci.getId() : null) +
               ", descriere='" + descriere + '\'' +
               ", valoare=" + valoare +
               ", blocat=" + blocat +
               '}';
    }
}