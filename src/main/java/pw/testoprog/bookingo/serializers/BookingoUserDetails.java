package pw.testoprog.bookingo.serializers;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pw.testoprog.bookingo.models.User;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class BookingoUserDetails implements UserDetails {

    private String emailAddress;
    private String password;
    private String firstName;
    private String lastName;
    private List<GrantedAuthority> authorities;
    private LocalDate createdOn;
    private boolean active;

    public BookingoUserDetails(User user) {
        this.emailAddress = user.getEmailAddress();
        this.password = user.getPassword();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.createdOn = user.getCreatedOn();
        this.authorities = Arrays.asList(new SimpleGrantedAuthority(user.getRole()));
        this.active = user.isActive();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return emailAddress;
    }

    @Override
    public boolean isAccountNonExpired() {
        return active;
    }

    @Override
    public boolean isAccountNonLocked() {
        return active;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return active;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }
}
