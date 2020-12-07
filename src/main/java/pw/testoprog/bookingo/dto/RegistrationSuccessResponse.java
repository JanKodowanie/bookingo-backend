package pw.testoprog.bookingo.dto;

import java.util.UUID;

public class RegistrationSuccessResponse {

    private UUID id;
    private String role;

    public RegistrationSuccessResponse(UUID id, String role) {
        this.id = id;
        this.role = role;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
