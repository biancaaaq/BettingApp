package proiect.bet.sportbet.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import proiect.bet.sportbet.models.Balanta;
import proiect.bet.sportbet.models.Tranzactie;
import proiect.bet.sportbet.models.Utilizator;
import proiect.bet.sportbet.repository.BalantaRepository;
import proiect.bet.sportbet.repository.TranzactieRepository;
import proiect.bet.sportbet.repository.UtilizatorRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TranzactieService {
    private final TranzactieRepository tranzactieRepository;
    private final UtilizatorRepository utilizatorRepository;
    private final BalantaRepository balantaRepository;

    public TranzactieService(TranzactieRepository tranzactieRepository, UtilizatorRepository utilizatorRepository, BalantaRepository balantaRepository) {
        this.tranzactieRepository = tranzactieRepository;
        this.utilizatorRepository = utilizatorRepository;
        this.balantaRepository = balantaRepository;
    }

    @Transactional
    public Tranzactie createTranzactie(Tranzactie tranzactie) {
        Utilizator utilizator = tranzactie.getUtilizator();
        if (utilizator == null) {
            throw new RuntimeException("Utilizatorul nu a fost specificat pentru tranzacție");
        }

        final Utilizator utilizatorFinal = utilizatorRepository.findById(utilizator.getId())
                .orElseThrow(() -> new RuntimeException("Utilizatorul nu a fost găsit"));

        Balanta balanta = balantaRepository.findByUtilizatorId(utilizatorFinal.getId())
                .orElseGet(() -> {
                    Balanta newBalanta = new Balanta();
                    newBalanta.setUtilizator(utilizatorFinal);
                    newBalanta.setSuma(0.0);
                    return balantaRepository.save(newBalanta);
                });

        Double valoare = tranzactie.getValoare();
        if (tranzactie.getTip() == Tranzactie.TipTranzactie.WITHDRAWAL) {
            if (balanta.getSuma() < valoare) {
                throw new RuntimeException("Fonduri insuficiente pentru retragere");
            }
            balanta.setSuma(balanta.getSuma() - valoare);
        } else if (tranzactie.getTip() == Tranzactie.TipTranzactie.DEPOSIT) {
            balanta.setSuma(balanta.getSuma() + valoare);
        } else {
            throw new RuntimeException("Tipul tranzacției este invalid");
        }

        balantaRepository.save(balanta);
        tranzactie.setUtilizator(utilizatorFinal);
        return tranzactieRepository.save(tranzactie);
    }

    public List<Tranzactie> getAllTranzactii() {
        return tranzactieRepository.findAll();
    }

    public Optional<Tranzactie> getTranzactieById(Long id) {
        return tranzactieRepository.findById(id);
    }

    public List<Tranzactie> getTranzactiiByUtilizatorId(Long utilizatorId) {
        return tranzactieRepository.findByUtilizatorId(utilizatorId);
    }

    public Double getBalantaUtilizator(Long utilizatorId) {
        return balantaRepository.findByUtilizatorId(utilizatorId)
                .map(Balanta::getSuma)
                .orElse(0.0);
    }

    public Tranzactie updateTranzactie(Long id, Tranzactie tranzactie) {
        if (!tranzactieRepository.existsById(id)) {
            throw new RuntimeException("Tranzacția nu a fost găsită");
        }
        tranzactie.setId(id);
        return tranzactieRepository.save(tranzactie);
    }

    public void deleteTranzactie(Long id) {
        if (!tranzactieRepository.existsById(id)) {
            throw new RuntimeException("Tranzacția nu a fost găsită");
        }
        tranzactieRepository.deleteById(id);
    }
}