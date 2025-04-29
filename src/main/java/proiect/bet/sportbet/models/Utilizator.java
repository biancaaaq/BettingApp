package proiect.bet.sportbet.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "utilizatori")
public class Utilizator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nume_utilizator", unique = true, nullable = false, length = 50)
    private String numeUtilizator;

    @Column(unique = true, nullable = false, length = 100)
    private String email;

    @Column(nullable = false, length = 255)
    private String parola;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol", nullable = false)
    private Rol rol = Rol.USER;

    @Column(nullable = false)
    private boolean activ = true;

    @Column(name = "data_creare", nullable = false)
    private LocalDateTime dataCreare = LocalDateTime.now();

    public enum Rol {
        ADMIN, USER
    }

    // Getteri și setteri
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNumeUtilizator() { return numeUtilizator; }
    public void setNumeUtilizator(String numeUtilizator) { this.numeUtilizator = numeUtilizator; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getParola() { return parola; }
    public void setParola(String parola) { this.parola = parola; }
    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }
    public boolean isActiv() { return activ; }
    public void setActiv(boolean activ) { this.activ = activ; }
    public LocalDateTime getDataCreare() { return dataCreare; }
    public void setDataCreare(LocalDateTime dataCreare) { this.dataCreare = dataCreare; }
}