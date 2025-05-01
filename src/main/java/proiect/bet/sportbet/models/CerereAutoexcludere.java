package proiect.bet.sportbet.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "cereri_autoexcludere")
public class CerereAutoexcludere {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "utilizator_id", nullable = false)
    private Utilizator utilizator;

    @Column(name = "cerere", nullable = false)
    private LocalDateTime cerere = LocalDateTime.now();

    @Column(nullable = false)
    private boolean aprobat;

    // Getteri și setteri
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Utilizator getUtilizator() { return utilizator; }
    public void setUtilizator(Utilizator utilizator) { this.utilizator = utilizator; }
    public LocalDateTime getCerere() { return cerere; }
    public void setCerere(LocalDateTime cerere) { this.cerere = cerere; }
    public boolean isAprobat() { return aprobat; }
    public void setAprobat(boolean aprobat) { this.aprobat = aprobat; }
}