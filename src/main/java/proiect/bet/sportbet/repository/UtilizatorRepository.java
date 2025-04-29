package proiect.bet.sportbet.repository;

import proiect.bet.sportbet.models.Utilizator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtilizatorRepository extends JpaRepository<Utilizator, Long> {
}