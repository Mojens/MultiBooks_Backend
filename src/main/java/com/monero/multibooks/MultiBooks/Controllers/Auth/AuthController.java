package com.monero.multibooks.MultiBooks.Controllers.Auth;

import com.monero.multibooks.MultiBooks.Dto.Auth.ForgotPasswordRequest;
import com.monero.multibooks.MultiBooks.Dto.Auth.LoginRequest;
import com.monero.multibooks.MultiBooks.Dto.Auth.LoginResponse;
import com.monero.multibooks.MultiBooks.Dto.Shared.ApiResponse;
import com.monero.multibooks.MultiBooks.Dto.User.UpdateUserRequest;
import com.monero.multibooks.MultiBooks.Dto.User.UserRequest;
import com.monero.multibooks.MultiBooks.Entities.User.User;
import com.monero.multibooks.MultiBooks.Service.Auth.AuthService;
import com.monero.multibooks.MultiBooks.Service.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.Arrays;
import java.util.UUID;

import static java.util.stream.Collectors.joining;

@RestController
@CrossOrigin
@RequestMapping("/api/auth/")
public class AuthController {

    @Value("${app.token-issuer}")
    private String tokenIssuer;

    @Value("${app.token-expiration}")
    private long tokenExpiration;

    private final AuthenticationManager authenticationManager;

    private final UserService userService;

    private final AuthService authService;

    @Autowired
    JwtEncoder encoder;

    public AuthController(AuthenticationManager authenticationManager,
                          UserService userService, AuthService authService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.authService = authService;
    }

    @PostMapping("login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequest request) {
        try {
            UsernamePasswordAuthenticationToken uat = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
            Authentication authentication = authenticationManager.authenticate(uat);

            User user = (User) authentication.getPrincipal();
            Instant now = Instant.now();

            String scope = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(joining(" "));

            JwtClaimsSet claims = JwtClaimsSet.builder()
                    .issuer(tokenIssuer)
                    .issuedAt(now)
                    .audience(Arrays.asList("not used"))
                    .expiresAt(now.plusSeconds(tokenExpiration))
                    .subject(user.getUsername())
                    .claim("roles", scope)
                    .build();

            JwsHeader jwsHeader = JwsHeader.with(() -> "HS256").type("JWT").build();

            String token = encoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();


            return ResponseEntity.ok()
                    .body(new ApiResponse(new LoginResponse(user.getUsername(),token),"You have successfully logged in"));
        } catch (BadCredentialsException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Username or password wrong");
        }
    }

    @PostMapping("register")
    public ResponseEntity<ApiResponse> register(@RequestBody UserRequest request){
        return ResponseEntity.ok()
                .body(userService.registerUser(request));
    }

    @PostMapping("forgot-password")
    public ResponseEntity<ApiResponse> forgotPassword(@RequestBody ForgotPasswordRequest request){
        System.out.println(request);
        User foundUser = userService.findUserByEmail(request.getEmail());
        if (foundUser == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Email not found");
        }
        String resetToken = UUID.randomUUID().toString();
        foundUser.setResetToken(resetToken);
        userService.updateUser(new UpdateUserRequest(foundUser));

        authService.sendPasswordResetEmail(request.getEmail(),resetToken);
        return ResponseEntity.ok(new ApiResponse("","Reset link sent to your email"));
    }

    @GetMapping("isAuthenticated")
    public ResponseEntity<ApiResponse> isAuthenticated(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return ResponseEntity.ok().body(new ApiResponse("User is authenticated.", ""));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse("User is not authenticated.", ""));
        }
    }

}
