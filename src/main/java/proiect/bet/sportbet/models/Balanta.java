package proiect.bet.sportbet.models;

import jakarta.persistence.*;

@Entity
@Table(name = "balanta")
public class Balanta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "utilizator_id", nullable = false)
    private Utilizator utilizator;

    @Column(nullable = false)
    private Double suma;

    // Getteri și setteri
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Utilizator getUtilizator() { return utilizator; }
    public void setUtilizator(Utilizator utilizator) { this.utilizator = utilizator; }
    public Double getSuma() { return suma; }
    public void setSuma(Double suma) { this.suma = suma; }
}