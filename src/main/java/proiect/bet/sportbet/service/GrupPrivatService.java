package proiect.bet.sportbet.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import proiect.bet.sportbet.models.*;
import proiect.bet.sportbet.repository.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GrupPrivatService {
    private final GrupPrivatRepository grupPrivatRepository;
    private final UtilizatorRepository utilizatorRepository;
    private final InvitatieGrupRepository invitatieGrupRepository;
    private final BiletRepository biletRepository;
    private final BalantaRepository balantaRepository;
    private final TranzactieRepository tranzactieRepository;
    private final GrupMemberRepository grupMemberRepository;

    public GrupPrivatService(
        GrupPrivatRepository grupPrivatRepository,
        UtilizatorRepository utilizatorRepository,
        InvitatieGrupRepository invitatieGrupRepository,
        BiletRepository biletRepository,
        BalantaRepository balantaRepository,
        TranzactieRepository tranzactieRepository,
        GrupMemberRepository grupMemberRepository
    ) {
        this.grupPrivatRepository = grupPrivatRepository;
        this.utilizatorRepository = utilizatorRepository;
        this.invitatieGrupRepository = invitatieGrupRepository;
        this.biletRepository = biletRepository;
        this.balantaRepository = balantaRepository;
        this.tranzactieRepository = tranzactieRepository;
        this.grupMemberRepository = grupMemberRepository;
    }

    public GrupPrivat createGrupPrivat(GrupPrivat grupPrivat, Double mizaComuna) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("Creare grup pentru utilizator: " + username);
        Utilizator admin = utilizatorRepository.findByNumeUtilizator(username)
            .orElseThrow(() -> new RuntimeException("Utilizatorul nu a fost găsit"));
        grupPrivat.setAdmin(admin);
        grupPrivat.setMizaComuna(mizaComuna);
        grupPrivat.setDataCreare(LocalDateTime.now());
        grupPrivat.setStatus(GrupPrivat.StatusGrup.IN_ASTEPTARE);
        grupPrivat = grupPrivatRepository.save(grupPrivat);

        System.out.println("Admin setat: " + admin.getNumeUtilizator() + ", ID: " + admin.getId());
        GrupMember grupMember = new GrupMember();
        grupMember.setGrup(grupPrivat);
        grupMember.setUtilizator(admin);
        grupMemberRepository.save(grupMember);
        System.out.println("Membru adăugat în grup_membri: Grup ID: " + grupPrivat.getId() + ", Utilizator ID: " + admin.getId());

        return grupPrivat;
    }

    public InvitatieGrup inviteUser(Long grupId, String usernameInvitat) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("Încercare invitare de către: " + username + ", pentru utilizator: " + usernameInvitat);
        Utilizator admin = utilizatorRepository.findByNumeUtilizator(username)
            .orElseThrow(() -> new RuntimeException("Adminul nu a fost găsit"));
        GrupPrivat grup = grupPrivatRepository.findById(grupId)
            .orElseThrow(() -> new RuntimeException("Grupul nu a fost găsit"));
        System.out.println("Admin grup: " + grup.getAdmin().getNumeUtilizator());
        if (!grup.getAdmin().equals(admin)) {
            System.out.println("Eroare: Utilizatorul " + username + " nu este adminul grupului");
            throw new RuntimeException("Doar adminul poate invita utilizatori");
        }
        Utilizator invitat = utilizatorRepository.findByNumeUtilizator(usernameInvitat)
            .orElseThrow(() -> new RuntimeException("Utilizatorul invitat nu a fost găsit"));
        if (grupMemberRepository.existsByGrupAndUtilizator(grup, invitat)) {
            throw new RuntimeException("Utilizatorul este deja membru al grupului");
        }
        InvitatieGrup invitatie = new InvitatieGrup();
        invitatie.setGrup(grup);
        invitatie.setUtilizator(invitat);
        invitatie.setDataInvitatie(LocalDateTime.now());
        invitatie.setStatus(InvitatieGrup.StatusInvitatie.PENDING);
        invitatie.setAcceptat(false);
        return invitatieGrupRepository.save(invitatie);
    }

    public void acceptInvitatie(Long invitatieId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Utilizator utilizator = utilizatorRepository.findByNumeUtilizator(username)
            .orElseThrow(() -> new RuntimeException("Utilizatorul nu a fost găsit"));
        InvitatieGrup invitatie = invitatieGrupRepository.findById(invitatieId)
            .orElseThrow(() -> new RuntimeException("Invitația nu a fost găsită"));
        if (!invitatie.getUtilizator().equals(utilizator)) {
            throw new RuntimeException("Doar utilizatorul invitat poate accepta invitația");
        }
        invitatie.setStatus(InvitatieGrup.StatusInvitatie.ACCEPTED);
        invitatie.setAcceptat(true);
        invitatieGrupRepository.save(invitatie);
        GrupPrivat grup = invitatie.getGrup();
        GrupMember grupMember = new GrupMember();
        grupMember.setGrup(grup);
        grupMember.setUtilizator(utilizator);
        grupMemberRepository.save(grupMember);
    }

    public void rejectInvitatie(Long invitatieId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Utilizator utilizator = utilizatorRepository.findByNumeUtilizator(username)
            .orElseThrow(() -> new RuntimeException("Utilizatorul nu a fost găsit"));
        InvitatieGrup invitatie = invitatieGrupRepository.findById(invitatieId)
            .orElseThrow(() -> new RuntimeException("Invitația nu a fost găsită"));
        if (!invitatie.getUtilizator().equals(utilizator)) {
            throw new RuntimeException("Doar utilizatorul invitat poate refuza invitația");
        }
        invitatie.setStatus(InvitatieGrup.StatusInvitatie.REJECTED);
        invitatie.setAcceptat(false);
        invitatieGrupRepository.save(invitatie);
    }

    public Bilet placeBiletInGrup(Long grupId, Bilet bilet) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Utilizator utilizator = utilizatorRepository.findByNumeUtilizator(username)
            .orElseThrow(() -> new RuntimeException("Utilizatorul nu a fost găsit"));
        GrupPrivat grup = grupPrivatRepository.findById(grupId)
            .orElseThrow(() -> new RuntimeException("Grupul nu a fost găsit"));
        System.out.println("Plasare bilet: Miză bilet = " + bilet.getMiza() + ", Miză comună = " + grup.getMizaComuna());
        if (Math.abs(bilet.getMiza() - grup.getMizaComuna()) > 0.01) { // Toleranță pentru zecimale
            throw new RuntimeException("Miza biletului trebuie să fie egală cu miza comună a grupului");
        }
        if (!grupMemberRepository.existsByGrupAndUtilizator(grup, utilizator)) {
            throw new RuntimeException("Utilizatorul nu este membru al grupului");
        }
        if (grup.getBilete().stream().anyMatch(b -> b.getUtilizator().equals(utilizator))) {
            throw new RuntimeException("Utilizatorul a plasat deja un bilet în acest grup");
        }
        bilet.setUtilizator(utilizator);
        bilet.setGrup(grup);
        bilet.setStatus(Bilet.Status.ACTIVE);
        bilet.setDataCreare(LocalDateTime.now());
        return biletRepository.save(bilet);
    }

    @Scheduled(cron = "0 0 0 * * ?") // La miezul nopții
    public void checkGroupResults() {
        List<GrupPrivat> grupuriActive = grupPrivatRepository.findByStatus(GrupPrivat.StatusGrup.ACTIV);
        for (GrupPrivat grup : grupuriActive) {
            List<Bilet> bilete = grup.getBilete();
            List<GrupMember> membri = grupMemberRepository.findByGrup(grup);
            if (bilete.size() != membri.size()) {
                continue; // Nu toate biletele au fost plasate
            }
            boolean allWon = bilete.stream().allMatch(b -> b.getStatus() == Bilet.Status.WON);
            grup.setStatus(GrupPrivat.StatusGrup.FINALIZAT);
            if (allWon) {
                double totalCastig = bilete.stream()
                    .mapToDouble(Bilet::getCastigPotential)
                    .sum();
                double castigPerMembru = totalCastig / membri.size();
                for (GrupMember membru : membri) {
                    Balanta balanta = balantaRepository.findByUtilizatorId(membru.getUtilizator().getId())
                        .orElseGet(() -> {
                            Balanta newBalanta = new Balanta();
                            newBalanta.setUtilizator(membru.getUtilizator());
                            newBalanta.setSuma(0.0);
                            return balantaRepository.save(newBalanta);
                        });
                    balanta.setSuma(balanta.getSuma() + castigPerMembru);
                    balantaRepository.save(balanta);
                    Tranzactie tranzactie = new Tranzactie();
                    tranzactie.setUtilizator(membru.getUtilizator());
                    tranzactie.setTip(Tranzactie.TipTranzactie.DEPOSIT);
                    tranzactie.setValoare(castigPerMembru);
                    tranzactie.setDataTranzactie(LocalDateTime.now());
                    tranzactieRepository.save(tranzactie);
                }
            }
            grupPrivatRepository.save(grup);
        }
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
            grupPrivat.setMizaComuna(updatedGrupPrivat.getMizaComuna());
            grupPrivat.setStatus(updatedGrupPrivat.getStatus());
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

    public List<GrupPrivat> getGrupuriByUtilizator() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("Caut utilizator: " + username);
        Utilizator utilizator = utilizatorRepository.findByNumeUtilizator(username)
            .orElseThrow(() -> new RuntimeException("Utilizatorul nu a fost găsit"));
        System.out.println("Utilizator găsit: " + utilizator.getNumeUtilizator() + ", ID: " + utilizator.getId());
        List<GrupMember> membri = grupMemberRepository.findByUtilizator(utilizator);
        System.out.println("Număr membri găsiți: " + membri.size() + ", Detalii: " + membri.stream().map(m -> m.getGrup().getNume()).collect(Collectors.joining(", ")));
        List<GrupPrivat> grupuri = membri.stream().map(GrupMember::getGrup).collect(Collectors.toList());
        System.out.println("Număr grupuri returnate: " + grupuri.size() + ", Detalii: " + grupuri.stream().map(GrupPrivat::getNume).collect(Collectors.joining(", ")));
        return grupuri;
    }

    public List<InvitatieGrup> getInvitatiiByUtilizator() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Utilizator utilizator = utilizatorRepository.findByNumeUtilizator(username)
            .orElseThrow(() -> new RuntimeException("Utilizatorul nu a fost găsit"));
        return invitatieGrupRepository.findByUtilizatorAndStatus(utilizator, InvitatieGrup.StatusInvitatie.PENDING);
    }
}