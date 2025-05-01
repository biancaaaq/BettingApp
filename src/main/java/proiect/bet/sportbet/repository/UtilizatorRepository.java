package proiect.bet.sportbet.repository;

import proiect.bet.sportbet.models.Utilizator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UtilizatorRepository extends JpaRepository<Utilizator, Long> {
    Optional<Utilizator> findByNumeUtilizator(String numeUtilizator);
}