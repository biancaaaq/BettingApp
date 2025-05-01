package proiect.bet.sportbet.models;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "promotii")
public class Promotie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titlu", nullable = false, length = 100)
    private String titlu;

    @Column(name = "descriere", nullable = false)
    private String descriere;

    @Column(name = "data_start", nullable = false)
    private LocalDate dataStart;

    @Column(name = "data_final", nullable = false)
    private LocalDate dataFinal;

    @Column(name = "imag")
    private String imag;

    // Getteri și setteri
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitlu() { return titlu; }
    public void setTitlu(String titlu) { this.titlu = titlu; }
    public String getDescriere() { return descriere; }
    public void setDescriere(String descriere) { this.descriere = descriere; }
    public LocalDate getDataStart() { return dataStart; }
    public void setDataStart(LocalDate dataStart) { this.dataStart = dataStart; }
    public LocalDate getDataFinal() { return dataFinal; }
    public void setDataFinal(LocalDate dataFinal) { this.dataFinal = dataFinal; }
    public String getImag() { return imag; }
    public void setImag(String imag) { this.imag = imag; }
}