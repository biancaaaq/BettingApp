package proiect.bet.sportbet.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import proiect.bet.sportbet.models.Utilizator;
import proiect.bet.sportbet.repository.UtilizatorRepository;
import proiect.bet.sportbet.security.JwtUtil;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UtilizatorRepository utilizatorRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UtilizatorRepository utilizatorRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.utilizatorRepository = utilizatorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequest authRequest) {
        System.out.println("Înregistrare utilizator: " + authRequest.getUsername());
        if (utilizatorRepository.findByNumeUtilizator(authRequest.getUsername()).isPresent()) {
            System.out.println("Utilizatorul există deja: " + authRequest.getUsername());
            return ResponseEntity.badRequest().body("Utilizatorul există deja!");
        }
        Utilizator utilizator = new Utilizator();
        utilizator.setNumeUtilizator(authRequest.getUsername());
        utilizator.setParola(passwordEncoder.encode(authRequest.getPassword()));
        utilizator.setEmail(authRequest.getEmail());
        utilizator.setRol(Utilizator.Rol.USER); // Setează rolul implicit ca USER
        utilizator.setActiv(true);
        utilizatorRepository.save(utilizator);
        System.out.println("Utilizator înregistrat: " + authRequest.getUsername());
        return ResponseEntity.ok("Utilizator înregistrat cu succes!");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        System.out.println("Încercare login pentru utilizator: " + authRequest.getUsername());
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Caută utilizatorul pentru a obține rolul
            Utilizator utilizator = utilizatorRepository.findByNumeUtilizator(authRequest.getUsername())
                    .orElseThrow(() -> new RuntimeException("Utilizatorul nu a fost găsit după autentificare"));
            String role = utilizator.getRol().toString();
            System.out.println("Rol utilizator: " + role);

            String jwt = jwtUtil.generateToken(authRequest.getUsername(), role);
            System.out.println("Login reușit pentru utilizator: " + authRequest.getUsername() + ", token: " + jwt);
            return ResponseEntity.ok(new AuthResponse(jwt));
        } catch (Exception e) {
            System.out.println("Eroare la login pentru utilizator: " + authRequest.getUsername() + ", eroare: " + e.getMessage());
            return ResponseEntity.badRequest().body("Eroare la autentificare: " + e.getMessage());
        }
    }
}