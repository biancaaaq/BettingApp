package proiect.bet.sportbet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import proiect.bet.sportbet.models.Balanta;

import java.util.Optional;

public interface BalantaRepository extends JpaRepository<Balanta, Long> {
    Optional<Balanta> findByUtilizatorId(Long utilizatorId);
}