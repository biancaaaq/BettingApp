package proiect.bet.sportbet.service;

import org.springframework.stereotype.Service;

import proiect.bet.sportbet.models.Bilet;
import proiect.bet.sportbet.models.Cota;
import proiect.bet.sportbet.models.Utilizator;
import proiect.bet.sportbet.repository.CotaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import proiect.bet.sportbet.repository.BiletCotaRepository;
import proiect.bet.sportbet.repository.BiletRepository;
import proiect.bet.sportbet.repository.BalantaRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Set;




@Service
public class CotaService {
    private final CotaRepository cotaRepository;
    private final BiletCotaRepository biletCotaRepository;
    private final BiletRepository biletRepository;
    private final BalantaRepository balantaRepository;



    @Autowired
    public CotaService(CotaRepository cotaRepository,
        BiletCotaRepository biletCotaRepository,
        BiletRepository biletRepository,
        
        BalantaRepository balantaRepository) {
    this.cotaRepository = cotaRepository;
    this.biletCotaRepository = biletCotaRepository;
    this.biletRepository = biletRepository;
    
    this.balantaRepository = balantaRepository;
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
    @Transactional
public void actualizeazaStatusCota(Long cotaId, String statusNou) {
    Cota cotaCastigatoare = cotaRepository.findById(cotaId)
        .orElseThrow(() -> new RuntimeException("Cota nu a fost găsită"));

    Long meciId = cotaCastigatoare.getMeci().getId();

    // 1. Preluăm toate cotele pentru acel meci
    List<Cota> toateCotele = cotaRepository.findByMeciId(meciId);

    // 2. Actualizăm statusul fiecărei cote
    for (Cota c : toateCotele) {
        if (c.getId().equals(cotaId)) {
            c.setStatusCota(statusNou);
        } else if ("castigator".equalsIgnoreCase(statusNou)) {
            c.setStatusCota("pierzator");
        }

        // ✅ Blocăm automat cota după ce i se stabilește statusul final
        if ("castigator".equalsIgnoreCase(c.getStatusCota()) || 
            "pierzator".equalsIgnoreCase(c.getStatusCota())) {
            c.setBlocat(true);
        }

        cotaRepository.save(c);
    }

    // 3. Obținem toate biletele care conțin vreo cotă de la acest meci
    Set<Long> cotaIds = toateCotele.stream().map(Cota::getId).collect(Collectors.toSet());
    Set<Bilet> bileteAfectate = new HashSet<>();
    for (Long idCota : cotaIds) {
        bileteAfectate.addAll(biletCotaRepository.findBileteByCotaId(idCota));
    }

    // 4. Reevaluăm fiecare bilet
    for (Bilet bilet : bileteAfectate) {
        List<Cota> coteleBiletului = biletCotaRepository.findCoteByBiletId(bilet.getId());

        if (coteleBiletului.stream().anyMatch(c -> "pierzator".equalsIgnoreCase(c.getStatusCota()))) {
            bilet.setStatus(Bilet.Status.LOST);
        } else if (coteleBiletului.stream().allMatch(c -> "castigator".equalsIgnoreCase(c.getStatusCota()))) {
            bilet.setStatus(Bilet.Status.WON);
            Utilizator utilizator = bilet.getUtilizator();
            balantaRepository.findByUtilizatorId(utilizator.getId())
                .ifPresent(balanta -> {
                    balanta.setSuma(balanta.getSuma() + bilet.getCastigPotential());
                    balantaRepository.save(balanta);
                });
        }

        biletRepository.save(bilet);
    }
}



}