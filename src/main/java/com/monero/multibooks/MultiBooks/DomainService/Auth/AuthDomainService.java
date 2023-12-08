package com.monero.multibooks.MultiBooks.DomainService.Auth;

import com.monero.multibooks.MultiBooks.Entities.UserTeam.UserTeam;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class AuthDomainService {

    private final JwtDecoder jwtDecoder;

    public AuthDomainService(JwtDecoder jwtDecoder) {
        this.jwtDecoder = jwtDecoder;
    }

    public String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }


    public String extractUserEmailFromToken(HttpServletRequest request) {
        String token = extractToken(request);
        Jwt jwt = jwtDecoder.decode(token);
        return jwt.getSubject();
    }

    public void validateUserTeam(List<UserTeam> userTeams, HttpServletRequest request) {
        String loggedInUserEmail = extractUserEmailFromToken(request);

        boolean isUserPartOfTeam = userTeams.stream()
                .anyMatch(userTeam -> userTeam.getUser().getEmail().equalsIgnoreCase(loggedInUserEmail));

        if (!isUserPartOfTeam) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
        }
    }

}
