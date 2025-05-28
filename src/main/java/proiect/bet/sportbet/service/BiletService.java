package proiect.bet.sportbet.service;

import org.springframework.stereotype.Service;
import proiect.bet.sportbet.models.Bilet;
import proiect.bet.sportbet.repository.BiletRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BiletService {
    private final BiletRepository biletRepository;

    public BiletService(BiletRepository biletRepository) {
        this.biletRepository = biletRepository;
    }

    public Bilet createBilet(Bilet bilet) {
        return biletRepository.save(bilet);
    }

    public List<Bilet> getAllBilete() {
        return biletRepository.findAll();
    }

    public Optional<Bilet> getBiletById(Long id) {
        return biletRepository.findById(id);
    }

    public Bilet updateBilet(Long id, Bilet bilet) {
        if (!biletRepository.existsById(id)) {
            throw new RuntimeException("Biletul nu a fost găsit");
        }
        bilet.setId(id);
        return biletRepository.save(bilet);
    }

    public void deleteBilet(Long id) {
        if (!biletRepository.existsById(id)) {
            throw new RuntimeException("Biletul nu a fost găsit");
        }
        biletRepository.deleteById(id);
    }
}