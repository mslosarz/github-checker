package pl.software2.service.githubchecker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Data
public class ErrorResponse {

    public static final ErrorResponse NOT_ACCEPTABLE_RESPONSE = createFor(HttpStatus.NOT_ACCEPTABLE, "Unsupported media type, only application/json is supported for now");

    @JsonIgnore
    private final HttpStatusCode status;

    private final String code;

    @JsonProperty("Message")
    private final String message;

    private ErrorResponse(HttpStatusCode status, String message) {
        this.status = status;
        this.code = "" + status.value();
        this.message = message;
    }

    public static ErrorResponse createFor(HttpStatusCode status, String message) {
        return new ErrorResponse(status, message);
    }
}
