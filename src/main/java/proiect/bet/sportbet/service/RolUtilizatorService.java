package proiect.bet.sportbet.service;

import org.springframework.stereotype.Service;
import proiect.bet.sportbet.models.RolUtilizator;
import proiect.bet.sportbet.repository.RolUtilizatorRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RolUtilizatorService {
    private final RolUtilizatorRepository rolUtilizatorRepository;

    public RolUtilizatorService(RolUtilizatorRepository rolUtilizatorRepository) {
        this.rolUtilizatorRepository = rolUtilizatorRepository;
    }

    public RolUtilizator createRolUtilizator(RolUtilizator rolUtilizator) {
        return rolUtilizatorRepository.save(rolUtilizator);
    }

    public List<RolUtilizator> getAllRolUtilizatori() {
        return rolUtilizatorRepository.findAll();
    }

    public Optional<RolUtilizator> getRolUtilizatorById(Long id) {
        return rolUtilizatorRepository.findById(id);
    }

    public RolUtilizator updateRolUtilizator(Long id, RolUtilizator updatedRolUtilizator) {
        Optional<RolUtilizator> existingRolUtilizator = rolUtilizatorRepository.findById(id);
        if (existingRolUtilizator.isPresent()) {
            RolUtilizator rolUtilizator = existingRolUtilizator.get();
            rolUtilizator.setGrup(updatedRolUtilizator.getGrup());
            rolUtilizator.setUtilizator(updatedRolUtilizator.getUtilizator());
            rolUtilizator.setActiv(updatedRolUtilizator.isActiv());
            return rolUtilizatorRepository.save(rolUtilizator);
        } else {
            throw new RuntimeException("Rol utilizator cu ID-ul " + id + " nu a fost găsit.");
        }
    }

    public void deleteRolUtilizator(Long id) {
        if (rolUtilizatorRepository.existsById(id)) {
            rolUtilizatorRepository.deleteById(id);
        } else {
            throw new RuntimeException("Rol utilizator cu ID-ul " + id + " nu a fost găsit.");
        }
    }
}