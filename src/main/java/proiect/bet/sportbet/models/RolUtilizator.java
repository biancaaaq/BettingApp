package proiect.bet.sportbet.models;

import jakarta.persistence.*;

@Entity
@Table(name = "rol_utilizatori")
public class RolUtilizator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_grup", nullable = false)
    private GrupPrivat grup;

    @ManyToOne
    @JoinColumn(name = "utilizator_id", nullable = false)
    private Utilizator utilizator;

    @Column(nullable = false)
    private boolean activ = true;

    // Getteri și setteri
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public GrupPrivat getGrup() { return grup; }
    public void setGrup(GrupPrivat grup) { this.grup = grup; }
    public Utilizator getUtilizator() { return utilizator; }
    public void setUtilizator(Utilizator utilizator) { this.utilizator = utilizator; }
    public boolean isActiv() { return activ; }
    public void setActiv(boolean activ) { this.activ = activ; }
}