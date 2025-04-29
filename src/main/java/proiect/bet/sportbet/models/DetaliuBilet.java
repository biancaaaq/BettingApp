package proiect.bet.sportbet.models;

import jakarta.persistence.*;

@Entity
@Table(name = "detalii_bilete")
public class DetaliuBilet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_bilet", nullable = false)
    private Bilet bilet;

    @ManyToOne
    @JoinColumn(name = "id_cota", nullable = false)
    private Cota cota;

    @Column(nullable = false)
    private String predictie;

    // Getteri și setteri
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Bilet getBilet() { return bilet; }
    public void setBilet(Bilet bilet) { this.bilet = bilet; }
    public Cota getCota() { return cota; }
    public void setCota(Cota cota) { this.cota = cota; }
    public String getPredictie() { return predictie; }
    public void setPredictie(String predictie) { this.predictie = predictie; }
}