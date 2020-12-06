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
import pw.testoprog.bookingo.dto.*;
import pw.testoprog.bookingo.exceptions.EmailAlreadyRegisteredException;
import pw.testoprog.bookingo.models.User;
import pw.testoprog.bookingo.services.BookingoUserDetailsService;
import pw.testoprog.bookingo.services.JWTManager;

import javax.validation.Valid;
import java.util.Arrays;

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
    public ResponseEntity<?> login(@RequestBody @Valid  LoginRequest data)  {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(data.getEmail(), data.getPassword())
            );
        } catch (BadCredentialsException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(Arrays.asList("Credentials provided are incorrect.")));
        }

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(data.getEmail());

        final String jwt = jwtManager.generateToken(userDetails);
        return ResponseEntity.ok(new LoginResponse(jwt));
    }

    @PostMapping("/register/standard")
    public ResponseEntity registerStandard(@Valid @RequestBody UserDTO userDTO)  {
        try {
            User newUser = userDetailsService.registerUser(userDTO, "Standard");
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new RegistrationSuccessResponse(newUser.getId(), newUser.getRole()));
        }
        catch (EmailAlreadyRegisteredException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(Arrays.asList("Account with this email already exists.")));
        }
    }

    @PostMapping("/register/entrepreneur")
    public ResponseEntity registerEntrepreneur(@Valid @RequestBody UserDTO userDTO)  {
        try {
            User newUser = userDetailsService.registerUser(userDTO, "Entrepreneur");
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new RegistrationSuccessResponse(newUser.getId(), newUser.getRole()));
        }
        catch (EmailAlreadyRegisteredException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(Arrays.asList("Account with this email already exists.")));
        }
    }

    @PostMapping("/register/admin")
    public ResponseEntity registerAdmin(@Valid @RequestBody UserDTO userDTO) {
        try {
            User newUser = userDetailsService.registerUser(userDTO, "Admin");
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new RegistrationSuccessResponse(newUser.getId(), newUser.getRole()));
        } catch (EmailAlreadyRegisteredException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(Arrays.asList("Account with this email already exists.")));
        }
    }
}
