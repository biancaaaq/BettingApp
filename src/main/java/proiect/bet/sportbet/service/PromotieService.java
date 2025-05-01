package proiect.bet.sportbet.service;

import org.springframework.stereotype.Service;
import proiect.bet.sportbet.models.Promotie;
import proiect.bet.sportbet.repository.PromotieRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PromotieService {
    private final PromotieRepository promotieRepository;

    public PromotieService(PromotieRepository promotieRepository) {
        this.promotieRepository = promotieRepository;
    }

    public Promotie createPromotie(Promotie promotie) {
        return promotieRepository.save(promotie);
    }

    public List<Promotie> getAllPromotii() {
        return promotieRepository.findAll();
    }

    public Optional<Promotie> getPromotieById(Long id) {
        return promotieRepository.findById(id);
    }

    public Promotie updatePromotie(Long id, Promotie updatedPromotie) {
        Optional<Promotie> existingPromotie = promotieRepository.findById(id);
        if (existingPromotie.isPresent()) {
            Promotie promotie = existingPromotie.get();
            promotie.setTitlu(updatedPromotie.getTitlu());
            promotie.setDescriere(updatedPromotie.getDescriere());
            promotie.setDataStart(updatedPromotie.getDataStart());
            promotie.setDataFinal(updatedPromotie.getDataFinal());
            promotie.setImag(updatedPromotie.getImag());
            return promotieRepository.save(promotie);
        } else {
            throw new RuntimeException("Promoția cu ID-ul " + id + " nu a fost găsită.");
        }
    }

    public void deletePromotie(Long id) {
        if (promotieRepository.existsById(id)) {
            promotieRepository.deleteById(id);
        } else {
            throw new RuntimeException("Promoția cu ID-ul " + id + " nu a fost găsită.");
        }
    }
}