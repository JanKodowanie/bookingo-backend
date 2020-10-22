package pw.testoprog.bookingo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pw.testoprog.bookingo.serializers.BookingoUserDetails;
import pw.testoprog.bookingo.models.User;
import pw.testoprog.bookingo.repositories.UserRepository;

import java.util.Optional;

@Service
public class BookingoUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    // can't change the method name, but we use emails instead of usernames
    @Override
    public UserDetails loadUserByUsername(String emailAddress) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmailAddress(emailAddress);
        if (!user.isPresent()) {
            throw new UsernameNotFoundException("User was not found.");
        }
        return user.map(BookingoUserDetails::new).get();
    }
}
