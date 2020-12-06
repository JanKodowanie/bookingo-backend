package pw.testoprog.bookingo.dto;
import javax.validation.constraints.NotEmpty;

public class LoginRequest {

    @NotEmpty(message = "Email must be provided.")
    private String email;

    @NotEmpty(message = "Password must be provided.")
    private String password;

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public LoginRequest() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
