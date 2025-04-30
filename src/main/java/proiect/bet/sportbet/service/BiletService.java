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

    public Bilet updateBilet(Long id, Bilet updatedBilet) {
        Optional<Bilet> existingBilet = biletRepository.findById(id);
        if (existingBilet.isPresent()) {
            Bilet bilet = existingBilet.get();
            bilet.setUtilizator(updatedBilet.getUtilizator());
            bilet.setGrup(updatedBilet.getGrup());
            bilet.setCotaTotala(updatedBilet.getCotaTotala());
            bilet.setMiza(updatedBilet.getMiza());
            bilet.setCastigPotential(updatedBilet.getCastigPotential());
            bilet.setStatus(updatedBilet.getStatus());
            bilet.setDataCreare(updatedBilet.getDataCreare());
            return biletRepository.save(bilet);
        } else {
            throw new RuntimeException("Biletul cu ID-ul " + id + " nu a fost găsit.");
        }
    }

    public void deleteBilet(Long id) {
        if (biletRepository.existsById(id)) {
            biletRepository.deleteById(id);
        } else {
            throw new RuntimeException("Biletul cu ID-ul " + id + " nu a fost găsit.");
        }
    }
}