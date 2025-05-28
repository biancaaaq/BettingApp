package proiect.bet.sportbet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import proiect.bet.sportbet.models.CerereAutoexcludere;

import java.util.List;

public interface CerereAutoexcludereRepository extends JpaRepository<CerereAutoexcludere, Long> {
    List<CerereAutoexcludere> findByUtilizatorId(Long utilizatorId);
    List<CerereAutoexcludere> findByAprobatFalse();
}