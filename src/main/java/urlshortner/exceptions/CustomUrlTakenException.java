package urlshortner.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class CustomUrlTakenException extends RuntimeException {
    public CustomUrlTakenException(String message) {
        super(message);
    }
}