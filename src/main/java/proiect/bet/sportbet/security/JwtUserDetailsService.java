package proiect.bet.sportbet.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import proiect.bet.sportbet.models.Utilizator;
import proiect.bet.sportbet.repository.UtilizatorRepository;

import java.util.Collections;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UtilizatorRepository utilizatorRepository;

    public JwtUserDetailsService(UtilizatorRepository utilizatorRepository) {
        this.utilizatorRepository = utilizatorRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Încărcare utilizator: " + username);
        Utilizator utilizator = utilizatorRepository.findByNumeUtilizator(username)
                .orElseThrow(() -> new UsernameNotFoundException("Utilizatorul nu a fost găsit: " + username));
        System.out.println("Utilizator găsit: " + utilizator.getNumeUtilizator() + ", parola: " + utilizator.getParola() + ", rol: " + utilizator.getRol());
        String role = "ROLE_" + utilizator.getRol().toString();
        System.out.println("Rol setat: " + role);
        return new User(
                utilizator.getNumeUtilizator(),
                utilizator.getParola(),
                Collections.singletonList(new org.springframework.security.core.authority.SimpleGrantedAuthority(role))
        );
    }
}