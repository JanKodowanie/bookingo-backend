package pw.testoprog.bookingo.models;

import pw.testoprog.bookingo.dto.UserDTO;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Entity
public class User {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(unique = true)
    private String emailAddress;

    private String password;

    private String firstName;

    private String lastName;

    @Column(nullable = false, updatable = false)
    private LocalDate createdOn = LocalDate.now();

    private boolean active = true;

    private String role;

    @OneToMany(targetEntity = Venue.class, mappedBy="user", cascade=CascadeType.ALL)
    private Set<Venue> venues;

    @OneToMany(targetEntity = Review.class, mappedBy="user", cascade=CascadeType.ALL)
    private Set<Review> reviews;

    public User() {}

    public User(String emailAddress, String password, String firstName, String lastName, String role) {
        this.emailAddress = emailAddress;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }

    public User(UserDTO userData, String password, String role) {
        this.emailAddress = userData.getEmailAddress();
        this.password = password;
        this.firstName = userData.getFirstName();
        this.lastName = userData.getLastName();
        this.role = role;
    }

    @Override
    public String toString() {
        return String.format("%s %s", this.firstName, this.lastName);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Set<Venue> getVenues() { return venues; }

    public void setVenues(Set<Venue> venues) { this.venues = venues; }

    public Set<Review> getReviews() { return reviews; }

    public void setReviews(Set<Review> reviews) { this.reviews = reviews; }
}
