package proiect.bet.sportbet.service;

import org.springframework.stereotype.Service;
import proiect.bet.sportbet.models.Balanta;
import proiect.bet.sportbet.repository.BalantaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BalantaService {
    private final BalantaRepository balantaRepository;

    public BalantaService(BalantaRepository balantaRepository) {
        this.balantaRepository = balantaRepository;
    }

    public Balanta createBalanta(Balanta balanta) {
        return balantaRepository.save(balanta);
    }

    public List<Balanta> getAllBalante() {
        return balantaRepository.findAll();
    }

    public Optional<Balanta> getBalantaById(Long id) {
        return balantaRepository.findById(id);
    }

    public Balanta updateBalanta(Long id, Balanta updatedBalanta) {
        Optional<Balanta> existingBalanta = balantaRepository.findById(id);
        if (existingBalanta.isPresent()) {
            Balanta balanta = existingBalanta.get();
            balanta.setUtilizator(updatedBalanta.getUtilizator());
            balanta.setSuma(updatedBalanta.getSuma());
            return balantaRepository.save(balanta);
        } else {
            throw new RuntimeException("Balanța cu ID-ul " + id + " nu a fost găsită.");
        }
    }

    public void deleteBalanta(Long id) {
        if (balantaRepository.existsById(id)) {
            balantaRepository.deleteById(id);
        } else {
            throw new RuntimeException("Balanța cu ID-ul " + id + " nu a fost găsită.");
        }
    }
}