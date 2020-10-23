package pw.testoprog.bookingo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pw.testoprog.bookingo.exceptions.UserNotFoundException;
import pw.testoprog.bookingo.models.User;
import pw.testoprog.bookingo.serializers.MessageResponse;
import pw.testoprog.bookingo.services.BookingoUserDetailsService;

import java.util.List;
import java.util.UUID;

@RestController
public class UserDataController {

    @Autowired
    private BookingoUserDetailsService userDetailsService;

    @GetMapping("/user/{id}")
    public ResponseEntity getUserDetails(@PathVariable UUID id) {
        User user = null;
        try {
             user = userDetailsService.getUserDetails(id);
        } catch (UserNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new MessageResponse("User with the given id was not found."));
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(user);
    }

    @PatchMapping("/user/{id}")
    public ResponseEntity editUserDetails(@PathVariable UUID id, @RequestBody User user_data) {
        User user = null;
        try {
            user = userDetailsService.getUserDetails(id);
            user = userDetailsService.updateUser(id, user_data);
        } catch (UserNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new MessageResponse("User with the given id was not found."));
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(user);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity deleteUser(@PathVariable UUID id) {
        User user = null;
        try {
            userDetailsService.deleteUser(id);
        } catch (UserNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new MessageResponse("User with the given id was not found."));
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponse("User was deleted successfully."));
    }

    @GetMapping("/user_list")
    public List<User> getUserList() {
        return userDetailsService.getAllUsers();
    }

}
