package proiect.bet.sportbet.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "bilete")
public class Bilet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "utilizator_id", nullable = false)
    private Utilizator utilizator;

    @ManyToOne
    @JoinColumn(name = "id_grup")
    private GrupPrivat grup;

    @Column(name = "cota_totala", nullable = false)
    private Double cotaTotala;

    @Column(nullable = false)
    private Double miza;

    @Column(name = "castig_potential", nullable = false)
    private String castigPotential;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Column(name = "data_creare", nullable = false)
    private LocalDateTime dataCreare = LocalDateTime.now();

    public enum Status {
        ACTIVE, WON, LOST, CANCELLED
    }

    // Getteri și setteri
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Utilizator getUtilizator() { return utilizator; }
    public void setUtilizator(Utilizator utilizator) { this.utilizator = utilizator; }
    public GrupPrivat getGrup() { return grup; }
    public void setGrup(GrupPrivat grup) { this.grup = grup; }
    public Double getCotaTotala() { return cotaTotala; }
    public void setCotaTotala(Double cotaTotala) { this.cotaTotala = cotaTotala; }
    public Double getMiza() { return miza; }
    public void setMiza(Double miza) { this.miza = miza; }
    public String getCastigPotential() { return castigPotential; }
    public void setCastigPotential(String castigPotential) { this.castigPotential = castigPotential; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
    public LocalDateTime getDataCreare() { return dataCreare; }
    public void setDataCreare(LocalDateTime dataCreare) { this.dataCreare = dataCreare; }
}