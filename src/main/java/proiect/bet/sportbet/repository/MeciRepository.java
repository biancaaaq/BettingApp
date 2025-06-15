package proiect.bet.sportbet.repository;

import proiect.bet.sportbet.models.Meci;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeciRepository extends JpaRepository<Meci, Long> {
    boolean existsByEchipaAcasaAndEchipaDeplasareAndDataMeci(String echipaAcasa, String echipaDeplasare, LocalDateTime dataMeci);
    List<Meci> findByDataMeciBetween(LocalDateTime start, LocalDateTime end);
}
