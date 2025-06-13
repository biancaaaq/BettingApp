package proiect.bet.sportbet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import proiect.bet.sportbet.models.Mesaj;

import java.util.List;

public interface MesajRepository extends JpaRepository<Mesaj, Long> {
    List<Mesaj> findByGrupIdOrderByTimestampAsc(Long grupId);
}