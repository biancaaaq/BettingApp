package proiect.bet.sportbet.service;

import org.springframework.stereotype.Service;
import proiect.bet.sportbet.models.DetaliuBilet;
import proiect.bet.sportbet.repository.DetaliuBiletRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DetaliuBiletService {
    private final DetaliuBiletRepository detaliuBiletRepository;

    public DetaliuBiletService(DetaliuBiletRepository detaliuBiletRepository) {
        this.detaliuBiletRepository = detaliuBiletRepository;
    }

    public DetaliuBilet createDetaliuBilet(DetaliuBilet detaliuBilet) {
        return detaliuBiletRepository.save(detaliuBilet);
    }

    public List<DetaliuBilet> getAllDetaliiBilete() {
        return detaliuBiletRepository.findAll();
    }

    public Optional<DetaliuBilet> getDetaliuBiletById(Long id) {
        return detaliuBiletRepository.findById(id);
    }

    public DetaliuBilet updateDetaliuBilet(Long id, DetaliuBilet updatedDetaliuBilet) {
        Optional<DetaliuBilet> existingDetaliuBilet = detaliuBiletRepository.findById(id);
        if (existingDetaliuBilet.isPresent()) {
            DetaliuBilet detaliuBilet = existingDetaliuBilet.get();
            detaliuBilet.setBilet(updatedDetaliuBilet.getBilet());
            detaliuBilet.setCota(updatedDetaliuBilet.getCota());
            detaliuBilet.setPredictie(updatedDetaliuBilet.getPredictie());
            return detaliuBiletRepository.save(detaliuBilet);
        } else {
            throw new RuntimeException("Detaliu bilet cu ID-ul " + id + " nu a fost găsit.");
        }
    }

    public void deleteDetaliuBilet(Long id) {
        if (detaliuBiletRepository.existsById(id)) {
            detaliuBiletRepository.deleteById(id);
        } else {
            throw new RuntimeException("Detaliu bilet cu ID-ul " + id + " nu a fost găsit.");
        }
    }
}