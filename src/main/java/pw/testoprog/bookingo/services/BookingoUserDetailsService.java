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
import pw.testoprog.bookingo.repositories.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
        return user.map(BookingoUserDetails::new).get();
    }

    public User registerUser(User user, String role) throws EmailAlreadyRegisteredException {
        Optional<User> alreadyRegisteredUser = userRepository.findByEmailAddress(user.getEmailAddress());

        if (alreadyRegisteredUser.isPresent()) {
            throw new EmailAlreadyRegisteredException("User with this email already exists");
        }

        String plain_pass = user.getPassword();
        user.setPassword(passwordEncoder.encode(plain_pass));
        user.setRole(role);
        userRepository.save(user);

        return user;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserDetails(UUID id) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new UserNotFoundException("User with this id was not found.");
        }
        return user.get();
    }

    public User updateUser(UUID id, User user_data) throws UserNotFoundException {
        Optional<User> user_optional = userRepository.findById(id);
        if (!user_optional.isPresent()) {
            throw new UserNotFoundException("User with this id was not found.");
        }

        User user = user_optional.get();
        if (user_data.getEmailAddress() != null) {
            user.setEmailAddress(user_data.getEmailAddress());
        }
        if (user_data.getFirstName() != null) {
            user.setEmailAddress(user_data.getFirstName());
        }
        if (user_data.getLastName() != null) {
            user.setEmailAddress(user_data.getLastName());
        }

        userRepository.save(user);
        return user;
    }

    public void deleteUser(UUID id) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new UserNotFoundException("User with this id was not found.");
        }

        userRepository.delete(user.get());
    }
}
