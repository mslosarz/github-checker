package pl.software2.service.githubchecker;

import okhttp3.mockwebserver.MockWebServer;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.reactive.function.client.WebClient;
import pl.software2.service.githubchecker.exceptions.GlobalExceptionHandler;

@TestConfiguration
@ComponentScan(
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = AppConfiguration.class)
)
@ContextConfiguration(classes = GlobalExceptionHandler.class)
public class TestAppConfiguration {
    private final MockWebServer mockWebServer = new MockWebServer();

    @Bean
    @Primary
    WebClient.Builder testWebClientBuilder() {
        return WebClient.builder().baseUrl(mockWebServer.url("/").toString());
    }

    @Bean
    MockWebServer mockWebServer() {
        return mockWebServer;
    }

}
