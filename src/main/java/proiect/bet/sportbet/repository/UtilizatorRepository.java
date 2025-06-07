package proiect.bet.sportbet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import proiect.bet.sportbet.models.Utilizator;

import java.util.Optional;

public interface UtilizatorRepository extends JpaRepository<Utilizator, Long> {
    @Query("SELECT u FROM Utilizator u WHERE LOWER(u.numeUtilizator) = LOWER(:numeUtilizator)")
    Optional<Utilizator> findByNumeUtilizator(String numeUtilizator);
}