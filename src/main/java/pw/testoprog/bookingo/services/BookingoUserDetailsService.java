package pw.testoprog.bookingo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pw.testoprog.bookingo.exceptions.EmailAlreadyRegisteredException;
import pw.testoprog.bookingo.exceptions.UserNotFoundException;
import pw.testoprog.bookingo.models.User;
import pw.testoprog.bookingo.dto.UserDTO;
import pw.testoprog.bookingo.repositories.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BookingoUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    // can't change the method name, but we use emails instead of usernames
    @Override
    public UserDetails loadUserByUsername(String emailAddress) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmailAddress(emailAddress);
        if (!user.isPresent()) {
            throw new UsernameNotFoundException("User was not found.");
        }
        return user.map(UserDTO::new).get();
    }

    public User registerUser(UserDTO userDTO, String role) throws EmailAlreadyRegisteredException {
        Optional<User> alreadyRegisteredUser = userRepository.findByEmailAddress(userDTO.getEmailAddress());

        if (alreadyRegisteredUser.isPresent()) {
            throw new EmailAlreadyRegisteredException("User with this email already exists");
        }

        String encodedPass = passwordEncoder.encode(userDTO.getPassword());
        User user = new User(userDTO, encodedPass, role);
        userRepository.save(user);

        return user;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<UserDTO> getAllUserDetails() {
        return userRepository.findAll().stream().map(UserDTO::new).collect(Collectors.toList());
    }

    public UserDTO getUserDetails(UUID id) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new UserNotFoundException("User with this id was not found.");
        }

        return new UserDTO(user.get());
    }

    public UserDTO updateUser(UUID id, UserDTO userDTO) throws UserNotFoundException, EmailAlreadyRegisteredException {
        Optional<User> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent()) {
            throw new UserNotFoundException("User with this id was not found.");
        }

        User user = userOptional.get();
        if (userDTO.getEmailAddress() != null) {
            Optional<User> alreadyRegisteredUser = userRepository.findByEmailAddress(userDTO.getEmailAddress());

            if (alreadyRegisteredUser.isPresent()) {
                throw new EmailAlreadyRegisteredException("User with this email already exists");
            }

            user.setEmailAddress(userDTO.getEmailAddress());
        }
        if (userDTO.getFirstName() != null) {
            user.setFirstName(userDTO.getFirstName());
        }
        if (userDTO.getLastName() != null) {
            user.setLastName(userDTO.getLastName());
        }

        userRepository.save(user);

        return new UserDTO(user);
    }

    public void deleteUser(UUID id) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new UserNotFoundException("User with this id was not found.");
        }

        userRepository.delete(user.get());
    }
}
