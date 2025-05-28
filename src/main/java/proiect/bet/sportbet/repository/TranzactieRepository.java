package proiect.bet.sportbet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import proiect.bet.sportbet.models.Tranzactie;

import java.util.List;

public interface TranzactieRepository extends JpaRepository<Tranzactie, Long> {
    List<Tranzactie> findByUtilizatorId(Long utilizatorId);
}