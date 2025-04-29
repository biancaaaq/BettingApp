package proiect.bet.sportbet.service;

import org.springframework.stereotype.Service;
import proiect.bet.sportbet.models.Utilizator;
import proiect.bet.sportbet.repository.UtilizatorRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UtilizatorService {
    private final UtilizatorRepository utilizatorRepository;

    public UtilizatorService(UtilizatorRepository utilizatorRepository) {
        this.utilizatorRepository = utilizatorRepository;
    }

    // Create
    public Utilizator createUtilizator(Utilizator utilizator) {
        return utilizatorRepository.save(utilizator);
    }

    // Read (all)
    public List<Utilizator> getAllUtilizatori() {
        return utilizatorRepository.findAll();
    }

    // Read (by ID)
    public Optional<Utilizator> getUtilizatorById(Long id) {
        return utilizatorRepository.findById(id);
    }

    // Update
    public Utilizator updateUtilizator(Long id, Utilizator updatedUtilizator) {
        Optional<Utilizator> existingUtilizator = utilizatorRepository.findById(id);
        if (existingUtilizator.isPresent()) {
            Utilizator utilizator = existingUtilizator.get();
            utilizator.setNumeUtilizator(updatedUtilizator.getNumeUtilizator());
            utilizator.setEmail(updatedUtilizator.getEmail());
            utilizator.setParola(updatedUtilizator.getParola());
            utilizator.setRol(updatedUtilizator.getRol());
            utilizator.setActiv(updatedUtilizator.isActiv());
            utilizator.setDataCreare(updatedUtilizator.getDataCreare());
            return utilizatorRepository.save(utilizator);
        } else {
            throw new RuntimeException("Utilizatorul cu ID-ul " + id + " nu a fost găsit.");
        }
    }

    // Delete
    public void deleteUtilizator(Long id) {
        if (utilizatorRepository.existsById(id)) {
            utilizatorRepository.deleteById(id);
        } else {
            throw new RuntimeException("Utilizatorul cu ID-ul " + id + " nu a fost găsit.");
        }
    }
}   