package pl.software2.service.githubchecker.exceptions;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;
import pl.software2.service.githubchecker.model.ErrorResponse;

public abstract class BusinessException extends ResponseStatusException {
    private final String message;
    public BusinessException(HttpStatusCode status, String message) {
        super(status, message);
        this.message = message;
    }

    public final ErrorResponse getResponse() {
        return ErrorResponse.createFor(getStatusCode(), message);
    }
}
