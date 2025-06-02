package proiect.bet.sportbet.service;

import org.springframework.stereotype.Service;
import proiect.bet.sportbet.models.CerereAutoexcludere;
import proiect.bet.sportbet.repository.CerereAutoexcludereRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CerereAutoexcludereService {
    private final CerereAutoexcludereRepository cerereAutoexcludereRepository;

    public CerereAutoexcludereService(CerereAutoexcludereRepository cerereAutoexcludereRepository) {
        this.cerereAutoexcludereRepository = cerereAutoexcludereRepository;
    }

    public CerereAutoexcludere createCerereAutoexcludere(CerereAutoexcludere cerereAutoexcludere) {
        return cerereAutoexcludereRepository.save(cerereAutoexcludere);
    }

    public List<CerereAutoexcludere> getAllCereriAutoexcludere() {
        return cerereAutoexcludereRepository.findAll();
    }

    public Optional<CerereAutoexcludere> getCerereAutoexcludereById(Long id) {
        return cerereAutoexcludereRepository.findById(id);
    }

    public CerereAutoexcludere updateCerereAutoexcludere(Long id, CerereAutoexcludere updatedCerereAutoexcludere) {
        Optional<CerereAutoexcludere> existingCerereAutoexcludere = cerereAutoexcludereRepository.findById(id);
        if (existingCerereAutoexcludere.isPresent()) {
            CerereAutoexcludere cerereAutoexcludere = existingCerereAutoexcludere.get();
            cerereAutoexcludere.setUtilizator(updatedCerereAutoexcludere.getUtilizator());
            cerereAutoexcludere.setCerere(updatedCerereAutoexcludere.getCerere());
            cerereAutoexcludere.setAprobat(updatedCerereAutoexcludere.isAprobat());
            return cerereAutoexcludereRepository.save(cerereAutoexcludere);
        } else {
            throw new RuntimeException("Cererea de autoexcludere cu ID-ul " + id + " nu a fost găsită.");
        }
    }

    public void deleteCerereAutoexcludere(Long id) {
        if (cerereAutoexcludereRepository.existsById(id)) {
            cerereAutoexcludereRepository.deleteById(id);
        } else {
            throw new RuntimeException("Cererea de autoexcludere cu ID-ul " + id + " nu a fost găsită.");
        }
    }

    public Optional<CerereAutoexcludere> getCerereAprobataByUtilizatorId(Long id) {
        return cerereAutoexcludereRepository.findByUtilizatorIdAndAprobatTrue(id);
    }
}