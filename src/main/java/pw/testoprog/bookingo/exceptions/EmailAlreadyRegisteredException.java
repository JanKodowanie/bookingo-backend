package pw.testoprog.bookingo.exceptions;

public class EmailAlreadyRegisteredException extends Exception {

    public EmailAlreadyRegisteredException(String message) {
        super(message);
    }
}
