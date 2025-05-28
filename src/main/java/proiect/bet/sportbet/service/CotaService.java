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

    public List<Cota> getCoteByMeciId(Long meciId) {
        return cotaRepository.findByMeciId(meciId);
    }

    public Cota updateCota(Long id, Cota cota) {
        if (!cotaRepository.existsById(id)) {
            throw new RuntimeException("Cota nu a fost găsită");
        }
        cota.setId(id);
        return cotaRepository.save(cota);
    }

    public void deleteCota(Long id) {
        if (!cotaRepository.existsById(id)) {
            throw new RuntimeException("Cota nu a fost găsită");
        }
        cotaRepository.deleteById(id);
    }
}