package proiect.bet.sportbet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import proiect.bet.sportbet.models.Cota;

import java.util.List;

public interface CotaRepository extends JpaRepository<Cota, Long> {
    List<Cota> findByMeciId(Long meciId);
}