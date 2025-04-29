package proiect.bet.sportbet.models;

import jakarta.persistence.*;

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
    private boolean blocat;

    // Getteri și setteri
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Meci getMeci() { return meci; }
    public void setMeci(Meci meci) { this.meci = meci; }
    public String getDescriere() { return descriere; }
    public void setDescriere(String descriere) { this.descriere = descriere; }
    public Double getValoare() { return valoare; }
    public void setValoare(Double valoare) { this.valoare = valoare; }
    public boolean isBlocat() { return blocat; }
    public void setBlocat(boolean blocat) { this.blocat = blocat; }
}