package proiect.bet.sportbet.service;

import org.springframework.stereotype.Service;
import proiect.bet.sportbet.models.GrupPrivat;
import proiect.bet.sportbet.repository.GrupPrivatRepository;

import java.util.List;
import java.util.Optional;

@Service
public class GrupPrivatService {
    private final GrupPrivatRepository grupPrivatRepository;

    public GrupPrivatService(GrupPrivatRepository grupPrivatRepository) {
        this.grupPrivatRepository = grupPrivatRepository;
    }

    public GrupPrivat createGrupPrivat(GrupPrivat grupPrivat) {
        return grupPrivatRepository.save(grupPrivat);
    }

    public List<GrupPrivat> getAllGrupuriPrivate() {
        return grupPrivatRepository.findAll();
    }

    public Optional<GrupPrivat> getGrupPrivatById(Long id) {
        return grupPrivatRepository.findById(id);
    }

    public GrupPrivat updateGrupPrivat(Long id, GrupPrivat updatedGrupPrivat) {
        Optional<GrupPrivat> existingGrupPrivat = grupPrivatRepository.findById(id);
        if (existingGrupPrivat.isPresent()) {
            GrupPrivat grupPrivat = existingGrupPrivat.get();
            grupPrivat.setNume(updatedGrupPrivat.getNume());
            grupPrivat.setAdmin(updatedGrupPrivat.getAdmin());
            grupPrivat.setLinkInvitatie(updatedGrupPrivat.getLinkInvitatie());
            grupPrivat.setDataCreare(updatedGrupPrivat.getDataCreare());
            return grupPrivatRepository.save(grupPrivat);
        } else {
            throw new RuntimeException("Grup privat cu ID-ul " + id + " nu a fost găsit.");
        }
    }

    public void deleteGrupPrivat(Long id) {
        if (grupPrivatRepository.existsById(id)) {
            grupPrivatRepository.deleteById(id);
        } else {
            throw new RuntimeException("Grup privat cu ID-ul " + id + " nu a fost găsit.");
        }
    }
}