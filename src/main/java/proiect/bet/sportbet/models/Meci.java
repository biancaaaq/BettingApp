package proiect.bet.sportbet.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "meciuri")
public class Meci {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "echipa_acasa", nullable = false)
    private String echipaAcasa;

    @Column(name = "echipa_deplasare", nullable = false)
    private String echipaDeplasare;

    @Column(name = "data_meci", nullable = false)
    private LocalDateTime dataMeci;

    @Column(nullable = false)
    private String competitie;

    @Column
    private String rezultat;

    @Column(nullable = false)
    private boolean blocat = false;

    @Column(nullable = false)
    private String status;

    // Getteri și setteri
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getEchipaAcasa() { return echipaAcasa; }
    public void setEchipaAcasa(String echipaAcasa) { this.echipaAcasa = echipaAcasa; }
    public String getEchipaDeplasare() { return echipaDeplasare; }
    public void setEchipaDeplasare(String echipaDeplasare) { this.echipaDeplasare = echipaDeplasare; }
    public LocalDateTime getDataMeci() { return dataMeci; }
    public void setDataMeci(LocalDateTime dataMeci) { this.dataMeci = dataMeci; }
    public String getCompetitie() { return competitie; }
    public void setCompetitie(String competitie) { this.competitie = competitie; }
    public String getRezultat() { return rezultat; }
    public void setRezultat(String rezultat) { this.rezultat = rezultat; }
    public boolean isBlocat() { return blocat; }
    public void setBlocat(boolean blocat) { this.blocat = blocat; }
    public void setStatus(String Status){ this.status=Status;}
    public String getStatus(){return this.status;}
}