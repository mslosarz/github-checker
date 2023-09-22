package pl.software2.service.githubchecker.exceptions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import pl.software2.service.githubchecker.model.ErrorResponse;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private ServerWebExchange exchange;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private BusinessException exception;

    @Mock
    private ResponseStatusException responseStatusException;

    @InjectMocks
    private GlobalExceptionHandler handler;

    @Test
    void shouldRunCustomHandlerForBusinessException() throws JsonProcessingException {
        // given
        when(exception.getResponse()).thenReturn(ErrorResponse.createFor(HttpStatus.OK, "ok"));

        // when
        handler.handle(exchange, exception);

        // then
        verify(exception, times(1)).getResponse();
        verify(objectMapper, times(1))
                .writeValueAsBytes(
                        argThat(arg -> arg instanceof ErrorResponse
                                && ((ErrorResponse) arg).getCode().equals("200")
                                && ((ErrorResponse) arg).getMessage().equals("ok"))
                );
    }

    @Test
    void shouldRunGenericHandler() throws JsonProcessingException {
        // given
        when(responseStatusException.getStatusCode()).thenReturn(HttpStatus.BAD_REQUEST);
        when(responseStatusException.getMessage()).thenReturn("boo");

        // when
        handler.handle(exchange, responseStatusException);

        // then
        verify(responseStatusException, times(2)).getStatusCode();
        verify(responseStatusException, times(1)).getMessage();
        verify(objectMapper, times(1))
                .writeValueAsBytes(
                        argThat(arg -> arg instanceof ErrorResponse
                                && ((ErrorResponse) arg).getCode().equals("400")
                                && ((ErrorResponse) arg).getMessage().equals("boo"))
                );
    }

}