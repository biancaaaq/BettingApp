package proiect.bet.sportbet.service;

import org.springframework.stereotype.Service;
import proiect.bet.sportbet.models.Tranzactie;
import proiect.bet.sportbet.repository.TranzactieRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TranzactieService {
    private final TranzactieRepository tranzactieRepository;

    public TranzactieService(TranzactieRepository tranzactieRepository) {
        this.tranzactieRepository = tranzactieRepository;
    }

    public Tranzactie createTranzactie(Tranzactie tranzactie) {
        return tranzactieRepository.save(tranzactie);
    }

    public List<Tranzactie> getAllTranzactii() {
        return tranzactieRepository.findAll();
    }

    public Optional<Tranzactie> getTranzactieById(Long id) {
        return tranzactieRepository.findById(id);
    }

    public Tranzactie updateTranzactie(Long id, Tranzactie updatedTranzactie) {
        Optional<Tranzactie> existingTranzactie = tranzactieRepository.findById(id);
        if (existingTranzactie.isPresent()) {
            Tranzactie tranzactie = existingTranzactie.get();
            tranzactie.setUtilizator(updatedTranzactie.getUtilizator());
            tranzactie.setTip(updatedTranzactie.getTip());
            tranzactie.setValoare(updatedTranzactie.getValoare());
            tranzactie.setDataTranzactie(updatedTranzactie.getDataTranzactie());
            return tranzactieRepository.save(tranzactie);
        } else {
            throw new RuntimeException("Tranzacția cu ID-ul " + id + " nu a fost găsită.");
        }
    }

    public void deleteTranzactie(Long id) {
        if (tranzactieRepository.existsById(id)) {
            tranzactieRepository.deleteById(id);
        } else {
            throw new RuntimeException("Tranzacția cu ID-ul " + id + " nu a fost găsită.");
        }
    }
}