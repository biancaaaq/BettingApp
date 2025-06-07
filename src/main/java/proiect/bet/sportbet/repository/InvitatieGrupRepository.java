package proiect.bet.sportbet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import proiect.bet.sportbet.models.InvitatieGrup;
import proiect.bet.sportbet.models.Utilizator;

import java.util.List;

public interface InvitatieGrupRepository extends JpaRepository<InvitatieGrup, Long> {
    List<InvitatieGrup> findByUtilizatorAndStatus(Utilizator utilizator, InvitatieGrup.StatusInvitatie status);
}