package proiect.bet.sportbet.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
    @JsonBackReference
    private GrupPrivat grup;

    @Column(name = "cota_totala", nullable = false)
    private Double cotaTotala;

    @Column(nullable = false)
    private Double miza;

    @Column(name = "castig_potential", nullable = false)
    private Double castigPotential; // Schimbat din String în Double

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Column(name = "data_creare", nullable = false)
    private LocalDateTime dataCreare = LocalDateTime.now();

    @ManyToMany
    @JoinTable(
        name = "bilet_cote",
        joinColumns = @JoinColumn(name = "bilet_id"),
        inverseJoinColumns = @JoinColumn(name = "cota_id")
    )
    private List<Cota> cote;

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
    public Double getCastigPotential() { return castigPotential; }
    public void setCastigPotential(Double castigPotential) { this.castigPotential = castigPotential; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
    public LocalDateTime getDataCreare() { return dataCreare; }
    public void setDataCreare(LocalDateTime dataCreare) { this.dataCreare = dataCreare; }
    public List<Cota> getCote() { return cote; }
    public void setCote(List<Cota> cote) { this.cote = cote; }
}