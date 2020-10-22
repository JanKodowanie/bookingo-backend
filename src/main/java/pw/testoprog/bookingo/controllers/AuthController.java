package pw.testoprog.bookingo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pw.testoprog.bookingo.serializers.LoginRequest;
import pw.testoprog.bookingo.serializers.LoginResponse;
import pw.testoprog.bookingo.services.BookingoUserDetailsService;
import pw.testoprog.bookingo.services.JWTManager;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private BookingoUserDetailsService userDetailsService;
    @Autowired
    private JWTManager jwtManager;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest data) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(data.getEmail(), data.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Credentials provided are incorrect.", e);
        }

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(data.getEmail());

        final String jwt = jwtManager.generateToken(userDetails);
        return ResponseEntity.ok(new LoginResponse(jwt));
    }

}
