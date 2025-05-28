package proiect.bet.sportbet.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "utilizatori")
public class Utilizator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nume_utilizator", nullable = false, unique = true)
    private String numeUtilizator;

    @Column(nullable = false)
    private String parola;

    @Column(nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Rol rol;

    @Column(nullable = false)
    private Boolean activ;

    @Column(name = "data_creare", nullable = false)
    private LocalDateTime dataCreare = LocalDateTime.now();

    public enum Rol {
        USER, ADMIN
    }

    // Constructori
    public Utilizator() {
    }

    public Utilizator(String numeUtilizator, String parola, String email, Rol rol, Boolean activ) {
        this.numeUtilizator = numeUtilizator;
        this.parola = parola;
        this.email = email;
        this.rol = rol;
        this.activ = activ;
    }

    // Getteri și setteri
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeUtilizator() {
        return numeUtilizator;
    }

    public void setNumeUtilizator(String numeUtilizator) {
        this.numeUtilizator = numeUtilizator;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Boolean getActiv() {
        return activ;
    }

    public void setActiv(Boolean activ) {
        this.activ = activ;
    }

    public LocalDateTime getDataCreare() {
        return dataCreare;
    }

    public void setDataCreare(LocalDateTime dataCreare) {
        this.dataCreare = dataCreare;
    }

    // Equals și hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Utilizator that = (Utilizator) o;
        return Objects.equals(id, that.id) &&
               Objects.equals(numeUtilizator, that.numeUtilizator) &&
               Objects.equals(parola, that.parola) &&
               Objects.equals(email, that.email) &&
               rol == that.rol &&
               Objects.equals(activ, that.activ) &&
               Objects.equals(dataCreare, that.dataCreare);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, numeUtilizator, parola, email, rol, activ, dataCreare);
    }

    // toString
    @Override
    public String toString() {
        return "Utilizator{" +
               "id=" + id +
               ", numeUtilizator='" + numeUtilizator + '\'' +
               ", email='" + email + '\'' +
               ", rol=" + rol +
               ", activ=" + activ +
               ", dataCreare=" + dataCreare +
               '}';
    }
}