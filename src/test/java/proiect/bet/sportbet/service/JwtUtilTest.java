package proiect.bet.sportbet.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import proiect.bet.sportbet.security.*;

class JwtUtilTest {

    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();
    }

    @Test
    void testGenerateAndValidateToken() {
        String username = "testUser";
        String role = "USER";

        UserDetails userDetails = new User(
            username,
            "password",
            true, true, true, true,
            Collections.emptyList()
        );

        String token = jwtUtil.generateToken(username, role);

        assertNotNull(token, "Token-ul generat nu trebuie să fie null");

        String extractedUsername = jwtUtil.getUsernameFromToken(token);
        assertEquals(username, extractedUsername, "Username-ul extras din token nu corespunde");

        String extractedRole = jwtUtil.getRoleFromToken(token);
        assertEquals(role, extractedRole, "Rolul extras din token nu corespunde");

        boolean isValid = jwtUtil.validateToken(token, userDetails);
        assertTrue(isValid, "Token-ul ar trebui să fie valid pentru utilizatorul dat");
    }

    @Test
    void testTokenExpiration() throws InterruptedException {
        jwtUtil = new JwtUtil();
        jwtUtil.setExpirationTime(1); // expirare rapidă

        String token = jwtUtil.generateToken("expiredUser", "USER");

        Thread.sleep(5); // lăsăm tokenul să expire

        UserDetails userDetails = new User("expiredUser", "pass", Collections.emptyList());

        boolean isValid;
        try {
            isValid = jwtUtil.validateToken(token, userDetails);
        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            isValid = false; // tratăm corect token-ul expirat
        }

        assertFalse(isValid, "Token-ul ar trebui să fie expirat și invalid");
    }

}
