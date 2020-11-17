package pw.testoprog.bookingo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BookingoFileNotFoundException extends Exception {
    public BookingoFileNotFoundException(String message) {
        super(message);
    }

    public BookingoFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
