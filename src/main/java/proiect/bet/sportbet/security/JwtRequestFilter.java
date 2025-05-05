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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    public JwtRequestFilter(UserDetailsService userDetailsService, JwtUtil jwtUtil) {
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            try {
                username = jwtUtil.getUsernameFromToken(jwt);
                System.out.println("Username extracted from token: " + username);
                String role = jwtUtil.getRoleFromToken(jwt);
                System.out.println("Role extracted from token: " + role);
            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get JWT Token: " + e.getMessage());
            } catch (ExpiredJwtException e) {
                System.out.println("JWT Token has expired: " + e.getMessage());
            } catch (SignatureException e) {
                System.out.println("JWT signature does not match: " + e.getMessage());
            } catch (MalformedJwtException e) {
                System.out.println("Invalid JWT token: " + e.getMessage());
            } catch (UnsupportedJwtException e) {
                System.out.println("Unsupported JWT token: " + e.getMessage());
            }
        } else {
            System.out.println("JWT Token does not begin with Bearer String");
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            System.out.println("UserDetails loaded: " + userDetails.getUsername() + ", Authorities: " + userDetails.getAuthorities());

            if (jwtUtil.validateToken(jwt)) {
                System.out.println("JWT Token is valid");
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                System.out.println("Authentication set for user: " + username);
            } else {
                System.out.println("JWT Token validation failed");
            }
        } else if (username == null) {
            System.out.println("Username could not be extracted from token");
        } else {
            System.out.println("Authentication already exists in SecurityContext");
        }

        chain.doFilter(request, response);
    }
}