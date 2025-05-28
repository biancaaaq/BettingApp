package proiect.bet.sportbet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import proiect.bet.sportbet.models.Bilet;

import java.util.List;

public interface BiletRepository extends JpaRepository<Bilet, Long> {
    List<Bilet> findByUtilizatorId(Long utilizatorId);
}