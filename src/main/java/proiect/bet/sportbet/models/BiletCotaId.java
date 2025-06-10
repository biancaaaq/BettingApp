package proiect.bet.sportbet.models;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class BiletCotaId implements Serializable {
    private Long biletId;
    private Long cotaId;

    public BiletCotaId() {}

    public BiletCotaId(Long biletId, Long cotaId) {
        this.biletId = biletId;
        this.cotaId = cotaId;
    }

    public Long getBiletId() {
        return biletId;
    }

    public Long getCotaId() {
        return cotaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BiletCotaId)) return false;
        BiletCotaId that = (BiletCotaId) o;
        return Objects.equals(biletId, that.biletId) &&
               Objects.equals(cotaId, that.cotaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(biletId, cotaId);
    }
}
