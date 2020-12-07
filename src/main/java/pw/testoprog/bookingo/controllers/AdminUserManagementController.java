package pw.testoprog.bookingo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pw.testoprog.bookingo.dto.UserDTO;
import pw.testoprog.bookingo.exceptions.EmailAlreadyRegisteredException;
import pw.testoprog.bookingo.exceptions.UserNotFoundException;
import pw.testoprog.bookingo.models.User;
import pw.testoprog.bookingo.dto.ErrorResponse;
import pw.testoprog.bookingo.dto.MessageResponse;
import pw.testoprog.bookingo.services.BookingoUserDetailsService;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RequestMapping("/admin")
@RestController
public class AdminUserManagementController {

    @Autowired
    private BookingoUserDetailsService userDetailsService;

    @GetMapping("/user/{id}")
    public ResponseEntity getUserDetails(@PathVariable UUID id) {
        try {
             UserDTO userDTO = userDetailsService.getUserDetails(id);
             return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(userDTO);
        } catch (UserNotFoundException e) {
             return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(Arrays.asList("User with the given id was not found.")));
        }
    }

    @PatchMapping("/user/{id}")
    public ResponseEntity editUserDetails(@PathVariable UUID id, @RequestBody UserDTO userDTO) {
        try {
            UserDTO newData = userDetailsService.updateUser(id, userDTO);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(newData);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(Arrays.asList(e.getMessage())));
        }
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity deleteUser(@PathVariable UUID id) {
        User user = null;
        try {
            userDetailsService.deleteUser(id);
        } catch (UserNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(Arrays.asList("User with the given id was not found.")));
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponse("User was deleted successfully."));
    }

    @GetMapping("/user-list")
    public List<UserDTO> getUserList() {
        return userDetailsService.getAllUserDetails();
    }

}
