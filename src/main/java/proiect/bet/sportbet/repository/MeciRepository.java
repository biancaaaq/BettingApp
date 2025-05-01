package proiect.bet.sportbet.repository;

import proiect.bet.sportbet.models.Meci;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeciRepository extends JpaRepository<Meci, Long> {
}