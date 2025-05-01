package proiect.bet.sportbet.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import proiect.bet.sportbet.models.Utilizator;
import proiect.bet.sportbet.repository.UtilizatorRepository;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UtilizatorRepository utilizatorRepository;

    public CustomUserDetailsService(UtilizatorRepository utilizatorRepository) {
        this.utilizatorRepository = utilizatorRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Încărcare utilizator: " + username);
        Optional<Utilizator> utilizatorOpt = utilizatorRepository.findByNumeUtilizator(username);
        if (utilizatorOpt.isEmpty()) {
            System.out.println("Utilizatorul " + username + " nu a fost găsit în baza de date.");
            throw new UsernameNotFoundException("Utilizatorul " + username + " nu a fost găsit.");
        }
        Utilizator utilizator = utilizatorOpt.get();
        System.out.println("Utilizator găsit: " + utilizator.getNumeUtilizator() + ", parola: " + utilizator.getParola() + ", rol: " + utilizator.getRol());
        return User.withUsername(utilizator.getNumeUtilizator())
                .password(utilizator.getParola())
                .roles(utilizator.getRol().toString())
                .build();
    }
}