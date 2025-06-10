package proiect.bet.sportbet.models;

import jakarta.persistence.*;

@Entity
@Table(name = "bilet_cote")
public class BiletCota {

    @EmbeddedId
    private BiletCotaId id;

    @ManyToOne
    @MapsId("biletId")
    @JoinColumn(name = "bilet_id")
    private Bilet bilet;

    @ManyToOne
    @MapsId("cotaId")
    @JoinColumn(name = "cota_id")
    private Cota cota;

    public BiletCota() {}

    public BiletCota(Bilet bilet, Cota cota) {
        this.bilet = bilet;
        this.cota = cota;
        this.id = new BiletCotaId(bilet.getId(), cota.getId());
    }

    public BiletCotaId getId() {
        return id;
    }

    public Bilet getBilet() {
        return bilet;
    }

    public Cota getCota() {
        return cota;
    }
}
