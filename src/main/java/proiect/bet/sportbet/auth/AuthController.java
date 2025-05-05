package proiect.bet.sportbet.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Autentificare", description = "Operațiuni pentru autentificarea utilizatorilor")
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
    @Operation(summary = "Înregistrează un utilizator nou", description = "Creează un utilizator nou în sistem")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Utilizator înregistrat cu succes"),
        @ApiResponse(responseCode = "400", description = "Utilizatorul există deja")
    })
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
        utilizator.setRol(Utilizator.Rol.USER);
        utilizator.setActiv(true);
        utilizatorRepository.save(utilizator);
        System.out.println("Utilizator înregistrat: " + authRequest.getUsername());
        return ResponseEntity.ok("Utilizator înregistrat cu succes!");
    }

    @PostMapping("/login")
    @Operation(summary = "Autentifică un utilizator", description = "Returnează un token JWT pentru un utilizator autentificat")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Autentificare reușită, token returnat"),
        @ApiResponse(responseCode = "400", description = "Credentiale invalide")
    })
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        System.out.println("Încercare login pentru utilizator: " + authRequest.getUsername());
        try {
            if (authRequest.getUsername() == null || authRequest.getPassword() == null) {
                System.out.println("Username sau parola lipsesc.");
                return ResponseEntity.badRequest().body("Username sau parola lipsesc.");
            }

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
            System.out.println("Autentificare reușită pentru utilizator: " + authRequest.getUsername());

            SecurityContextHolder.getContext().setAuthentication(authentication);

            Utilizator utilizator = utilizatorRepository.findByNumeUtilizator(authRequest.getUsername())
                    .orElseThrow(() -> {
                        System.out.println("Utilizatorul nu a fost găsit după autentificare: " + authRequest.getUsername());
                        return new RuntimeException("Utilizatorul nu a fost găsit după autentificare");
                    });
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