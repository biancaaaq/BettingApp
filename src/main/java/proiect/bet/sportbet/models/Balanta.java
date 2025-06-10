package proiect.bet.sportbet.models;

import jakarta.persistence.*;

@Entity
@Table(name = "balanta")
public class Balanta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double suma;

    @OneToOne
    @JoinColumn(name = "utilizator_id", nullable = false)
    private Utilizator utilizator;

    public Balanta() {}

    public Balanta(Utilizator utilizator, Double suma) {
        this.utilizator = utilizator;
        this.suma = suma;
    }

    public Long getId() { return id; }

    public Double getSuma() { return suma; }
    public void setSuma(Double suma) { this.suma = suma; }

    public Utilizator getUtilizator() { return utilizator; }
    public void setUtilizator(Utilizator utilizator) { this.utilizator = utilizator; }
}
