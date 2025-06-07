package proiect.bet.sportbet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import proiect.bet.sportbet.models.GrupPrivat;

import java.util.List;

public interface GrupPrivatRepository extends JpaRepository<GrupPrivat, Long> {
    List<GrupPrivat> findByStatus(GrupPrivat.StatusGrup status);
}