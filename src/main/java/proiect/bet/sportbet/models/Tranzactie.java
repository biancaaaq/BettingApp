package proiect.bet.sportbet.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tranzactii")
public class Tranzactie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "utilizator_id", nullable = false)
    private Utilizator utilizator;

    @Enumerated(EnumType.STRING)
    @Column(name = "tip", nullable = false)
    private TipTranzactie tip;

    @Column(nullable = false)
    private Double valoare;

    @Column(name = "data_tranzactie", nullable = false)
    private LocalDateTime dataTranzactie = LocalDateTime.now();

    public enum TipTranzactie {
        DEPOSIT, WITHDRAWAL
    }

    // Getteri și setteri
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Utilizator getUtilizator() { return utilizator; }
    public void setUtilizator(Utilizator utilizator) { this.utilizator = utilizator; }
    public TipTranzactie getTip() { return tip; }
    public void setTip(TipTranzactie tip) { this.tip = tip; }
    public Double getValoare() { return valoare; }
    public void setValoare(Double valoare) { this.valoare = valoare; }
    public LocalDateTime getDataTranzactie() { return dataTranzactie; }
    public void setDataTranzactie(LocalDateTime dataTranzactie) { this.dataTranzactie = dataTranzactie; }
}