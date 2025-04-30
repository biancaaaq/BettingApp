package proiect.bet.sportbet.service;

import org.springframework.stereotype.Service;
import proiect.bet.sportbet.models.Cota;
import proiect.bet.sportbet.repository.CotaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CotaService {
    private final CotaRepository cotaRepository;

    public CotaService(CotaRepository cotaRepository) {
        this.cotaRepository = cotaRepository;
    }

    public Cota createCota(Cota cota) {
        return cotaRepository.save(cota);
    }

    public List<Cota> getAllCote() {
        return cotaRepository.findAll();
    }

    public Optional<Cota> getCotaById(Long id) {
        return cotaRepository.findById(id);
    }

    public Cota updateCota(Long id, Cota updatedCota) {
        Optional<Cota> existingCota = cotaRepository.findById(id);
        if (existingCota.isPresent()) {
            Cota cota = existingCota.get();
            cota.setMeci(updatedCota.getMeci());
            cota.setDescriere(updatedCota.getDescriere());
            cota.setValoare(updatedCota.getValoare());
            cota.setBlocat(updatedCota.isBlocat());
            return cotaRepository.save(cota);
        } else {
            throw new RuntimeException("Cota cu ID-ul " + id + " nu a fost găsită.");
        }
    }

    public void deleteCota(Long id) {
        if (cotaRepository.existsById(id)) {
            cotaRepository.deleteById(id);
        } else {
            throw new RuntimeException("Cota cu ID-ul " + id + " nu a fost găsită.");
        }
    }
}