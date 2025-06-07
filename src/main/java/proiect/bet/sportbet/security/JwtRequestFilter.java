package proiect.bet.sportbet.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    private final JwtUserDetailsService jwtUserDetailsService;
    private final JwtUtil jwtUtil;

    public JwtRequestFilter(JwtUserDetailsService jwtUserDetailsService, JwtUtil jwtUtil) {
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        // Permite cererile către /api/auth/login fără verificare
        if (request.getRequestURI().startsWith("/api/auth/login")) {
            chain.doFilter(request, response);
            return;
        }

        final String requestTokenHeader = request.getHeader("Authorization");

        String username = null;
        String jwtToken = null;

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                username = jwtUtil.getUsernameFromToken(jwtToken);
                System.out.println("Username extras din token: " + username);
            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get JWT Token");
            } catch (ExpiredJwtException e) {
                System.out.println("JWT Token has expired");
            } catch (SignatureException | MalformedJwtException | UnsupportedJwtException e) {
                System.out.println("JWT Token validation failed");
            }
        } else {
            System.out.println("JWT Token does not begin with Bearer String");
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
    UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);
    System.out.println("Încărcare utilizator: " + username + ", Activ: " + userDetails.isEnabled());
    if (jwtUtil.validateToken(jwtToken, userDetails)) {
            if (!userDetails.isEnabled()) {
                System.out.println("Contul utilizatorului " + username + " este autoexclus");
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write("Contul este autoexclus");
                return;
            }
            System.out.println("Autorități utilizator: " + userDetails.getAuthorities());
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            usernamePasswordAuthenticationToken
                    .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        } else {
            System.out.println("Token invalid pentru utilizator: " + username);
        }
    }
        chain.doFilter(request, response);
    }
}