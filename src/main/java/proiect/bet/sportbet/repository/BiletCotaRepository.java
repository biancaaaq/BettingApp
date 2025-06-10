package proiect.bet.sportbet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import proiect.bet.sportbet.models.BiletCota;

import java.util.List;

public interface BiletCotaRepository extends JpaRepository<BiletCota, Long> {

    @Query("SELECT DISTINCT bc.bilet.id FROM BiletCota bc WHERE bc.cota.id = :cotaId")
    List<Long> findDistinctBiletIdsByCotaId(@Param("cotaId") Long cotaId);

    @Query("SELECT bc.cota FROM BiletCota bc WHERE bc.bilet.id = :biletId")
    List<proiect.bet.sportbet.models.Cota> findCoteByBiletId(@Param("biletId") Long biletId);

    @Query("SELECT bc.bilet FROM BiletCota bc WHERE bc.cota.id = :cotaId")
    List<proiect.bet.sportbet.models.Bilet> findBileteByCotaId(@Param("cotaId") Long cotaId);

}
