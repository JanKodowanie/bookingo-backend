package pw.testoprog.bookingo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pw.testoprog.bookingo.exceptions.EmailAlreadyRegisteredException;
import pw.testoprog.bookingo.models.User;
import pw.testoprog.bookingo.responses.LoginRequest;
import pw.testoprog.bookingo.responses.LoginResponse;
import pw.testoprog.bookingo.responses.ErrorResponse;
import pw.testoprog.bookingo.responses.RegistrationSuccessResponse;
import pw.testoprog.bookingo.services.BookingoUserDetailsService;
import pw.testoprog.bookingo.services.JWTManager;

import javax.validation.Valid;

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
    public ResponseEntity<?> login(@RequestBody LoginRequest data)  {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(data.getEmail(), data.getPassword())
            );
        } catch (BadCredentialsException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Credentials provided are incorrect."));
        }

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(data.getEmail());

        final String jwt = jwtManager.generateToken(userDetails);
        return ResponseEntity.ok(new LoginResponse(jwt));
    }

    @PostMapping("/register/standard")
    public ResponseEntity registerStandard(@Valid @RequestBody User newUser)  {
        try {
            newUser = userDetailsService.registerUser(newUser, "Standard");
        }
        catch (EmailAlreadyRegisteredException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Account with this email already exists."));
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new RegistrationSuccessResponse(newUser.getId(), newUser.getRole()));
    }

    @PostMapping("/register/entrepreneur")
    public ResponseEntity registerEntrepreneur(@Valid @RequestBody User newUser)  {
        try {
            newUser = userDetailsService.registerUser(newUser, "Entrepreneur");
        }
        catch (EmailAlreadyRegisteredException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Account with this email already exists."));
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new RegistrationSuccessResponse(newUser.getId(), newUser.getRole()));
    }

    @PostMapping("/register/admin")
    public ResponseEntity registerAdmin(@Valid @RequestBody User newUser)  {
        try {
            newUser = userDetailsService.registerUser(newUser, "Admin");
        }
        catch (EmailAlreadyRegisteredException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Account with this email already exists."));
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new RegistrationSuccessResponse(newUser.getId(), newUser.getRole()));
    }

}
