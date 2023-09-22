package pl.software2.service.githubchecker.exceptions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import pl.software2.service.githubchecker.model.ErrorResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static pl.software2.service.githubchecker.model.ErrorResponse.NOT_ACCEPTABLE_RESPONSE;
import static pl.software2.service.githubchecker.model.ErrorResponse.createFor;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler implements WebExceptionHandler {

    private final ObjectMapper objectMapper;

    @Override
    public Mono<Void> handle(final ServerWebExchange exchange, final Throwable throwable) {
        try {
            if (throwable instanceof BusinessException exception) {
                return writeErrorResponse(exchange, exception.getResponse());
            }

            if (throwable instanceof ResponseStatusException exception) {
                ErrorResponse response = switch (exception.getStatusCode().value()) {
                    case 404 -> createFor(exception.getStatusCode(), exchange.getRequest().getPath() + " was not found");
                    case 406 -> NOT_ACCEPTABLE_RESPONSE;
                    default -> createFor(exception.getStatusCode(), exception.getMessage());
                };
                return writeErrorResponse(exchange, response);
            }
        } catch (Throwable ignored) {

        }
        return Mono.error(throwable);

    }

    private Mono<Void> writeErrorResponse(ServerWebExchange exchange, ErrorResponse errorResponse) throws JsonProcessingException {
        exchange.getResponse().setStatusCode(errorResponse.getStatus());
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
        var buffer = exchange.getResponse().bufferFactory()
                .wrap(objectMapper.writeValueAsBytes(errorResponse));
        return exchange.getResponse().writeWith(Flux.just(buffer));
    }
}