package com.monero.multibooks.MultiBooks.Controllers.Auth;

import com.monero.multibooks.MultiBooks.Dto.Auth.LoginRequest;
import com.monero.multibooks.MultiBooks.Dto.Auth.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/auth/")
public class AuthController {

    @Value("${app.token-issuer}")
    private String tokenIssuer;

    @Value("${app.token-expiration}")
    private long tokenExpiration;

    private final AuthenticationManager authenticationManager;

    @Autowired
    JwtEncoder encoder;

    public AuthController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {

    }
}
