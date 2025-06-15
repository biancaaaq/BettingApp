package proiect.bet.sportbet.service;

import org.springframework.stereotype.Service;
import proiect.bet.sportbet.models.Meci;
import proiect.bet.sportbet.repository.MeciRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MeciService {
    private final MeciRepository meciRepository;

    public MeciService(MeciRepository meciRepository) {
        this.meciRepository = meciRepository;
    }

    public Meci createMeci(Meci meci) {
        return meciRepository.save(meci);
    }

    public List<Meci> getAllMeciuri() {
        return meciRepository.findAll();
    }

    public Optional<Meci> getMeciById(Long id) {
        return meciRepository.findById(id);
    }

    public Meci updateMeci(Long id, Meci updatedMeci) {
        Optional<Meci> existingMeci = meciRepository.findById(id);
        if (existingMeci.isPresent()) {
            Meci meci = existingMeci.get();
            meci.setEchipaAcasa(updatedMeci.getEchipaAcasa());
            meci.setEchipaDeplasare(updatedMeci.getEchipaDeplasare());
            meci.setDataMeci(updatedMeci.getDataMeci());
            meci.setCompetitie(updatedMeci.getCompetitie());
            meci.setRezultat(updatedMeci.getRezultat());
            meci.setBlocat(updatedMeci.isBlocat());
            return meciRepository.save(meci);
        } else {
            throw new RuntimeException("Meciul cu ID-ul " + id + " nu a fost găsit.");
        }
    }

    public void deleteMeci(Long id) {
        if (meciRepository.existsById(id)) {
            meciRepository.deleteById(id);
        } else {
            throw new RuntimeException("Meciul cu ID-ul " + id + " nu a fost găsit.");
        }
    }

    public List<Meci> getLiveMatchesToday() {
    LocalDate azi = LocalDate.now();
    LocalDateTime start = azi.atStartOfDay();
    LocalDateTime end = azi.plusDays(1).atStartOfDay().minusNanos(1);
    return meciRepository.findByDataMeciBetween(start, end);
}



}