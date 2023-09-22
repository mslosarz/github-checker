package pl.software2.service.githubchecker;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@ComponentScan
public class AppConfiguration {
    @Bean
    ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    WebClient.Builder webClientBuilder(@Value("${github.key}") String githubKey){
        return WebClient.builder().baseUrl("https://api.github.com/")
                .defaultHeader("Accept", "application/vnd.github+json")
                .defaultHeader("Authorization", "Bearer " + githubKey)
                .defaultHeader("X-GitHub-Api-Version", "2022-11-28");
    }
}
