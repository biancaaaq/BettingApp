package proiect.bet.sportbet.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import proiect.bet.sportbet.models.Utilizator;
import proiect.bet.sportbet.repository.UtilizatorRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UtilizatorService {
    private final UtilizatorRepository utilizatorRepository;
    private final PasswordEncoder passwordEncoder;

    public UtilizatorService(UtilizatorRepository utilizatorRepository, PasswordEncoder passwordEncoder) {
        this.utilizatorRepository = utilizatorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Utilizator createUtilizator(Utilizator utilizator) {
        // Criptează parola înainte de a salva utilizatorul
        utilizator.setParola(passwordEncoder.encode(utilizator.getParola()));
        return utilizatorRepository.save(utilizator);
    }

    public List<Utilizator> getAllUtilizatori() {
        return utilizatorRepository.findAll();
    }

    public Optional<Utilizator> getUtilizatorById(Long id) {
        return utilizatorRepository.findById(id);
    }

    public Utilizator updateUtilizator(Long id, Utilizator utilizator) {
        if (!utilizatorRepository.existsById(id)) {
            throw new RuntimeException("Utilizatorul nu a fost găsit");
        }
        utilizator.setId(id);
        // Criptează parola dacă este actualizată
        utilizator.setParola(passwordEncoder.encode(utilizator.getParola()));
        return utilizatorRepository.save(utilizator);
    }

    public void deleteUtilizator(Long id) {
        if (!utilizatorRepository.existsById(id)) {
            throw new RuntimeException("Utilizatorul nu a fost găsit");
        }
        utilizatorRepository.deleteById(id);
    }
}