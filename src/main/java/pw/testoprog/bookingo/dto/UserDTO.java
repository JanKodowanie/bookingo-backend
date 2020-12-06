package pw.testoprog.bookingo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import pw.testoprog.bookingo.models.User;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.UUID;

public class UserDTO {

    private UUID id;

    @NotEmpty(message = "Email address must be provided.")
    @JsonProperty("email")
    private String emailAddress;

    @NotEmpty(message = "Password must be provided.")
    private String password;

    @NotEmpty(message = "First name must be provided.")
    @JsonProperty("first_name")
    private String firstName;

    @NotEmpty(message = "Last name must be provided.")
    @JsonProperty("last_name")
    private String lastName;

    private String role;

    @JsonProperty("created_on")
    private LocalDate createdOn;

    private boolean active;

    public UserDTO() {

    }

    public UserDTO(String emailAddress, String password, String firstName, String lastName) {
        this.emailAddress = emailAddress;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public UserDTO(User user) {
        this.id = user.getId();
        this.emailAddress = user.getEmailAddress();
        this.password = user.getPassword();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.role = user.getRole();
        this.createdOn = user.getCreatedOn();
        this.active = user.isActive();
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

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty("password")
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

    public void setActive(Boolean active) {
        this.active = active;
    }
}
