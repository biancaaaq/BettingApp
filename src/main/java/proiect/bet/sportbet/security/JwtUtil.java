package proiect.bet.sportbet.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    private String secret = "secretKey123"; // Cheie secretă (schimb-o cu una mai sigură în producție)
    private long expirationTime = 1000 * 60 * 60 * 10; // 10 ore (în milisecunde)

    public String generateToken(String username, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role); // Rolul este inclus în claim-uri
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    public String getUsernameFromToken(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    public String getRoleFromToken(String token) {
        return getClaimsFromToken(token).get("role", String.class);
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        Claims claims = getClaimsFromToken(token);
        Date expiration = claims.getExpiration();
        boolean isTokenExpired = expiration.before(new Date());
        return (username.equals(userDetails.getUsername()) && !isTokenExpired);
    }

    public void setExpirationTime(long expirationTime) {
    this.expirationTime = expirationTime;
}

}