package pw.testoprog.bookingo.serializers;

public class LoginResponse {

    private final String jwt;

    public LoginResponse(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }

}
