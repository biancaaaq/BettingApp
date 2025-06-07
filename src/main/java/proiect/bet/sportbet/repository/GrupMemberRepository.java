package proiect.bet.sportbet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import proiect.bet.sportbet.models.GrupMember;
import proiect.bet.sportbet.models.GrupPrivat;
import proiect.bet.sportbet.models.Utilizator;

import java.util.List;

public interface GrupMemberRepository extends JpaRepository<GrupMember, Long> {
    List<GrupMember> findByGrup(GrupPrivat grup);
    List<GrupMember> findByUtilizator(Utilizator utilizator);
    boolean existsByGrupAndUtilizator(GrupPrivat grup, Utilizator utilizator);
}