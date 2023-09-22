package pl.software2.service.githubchecker.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import pl.software2.service.githubchecker.exceptions.UserNotFoundException;
import pl.software2.service.githubchecker.exceptions.UserRepositoryNotFoundException;
import pl.software2.service.githubchecker.model.github.RepoBranch;
import pl.software2.service.githubchecker.model.github.UserRepo;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Function;

import static reactor.core.publisher.Mono.error;

@Repository
public class GithubRepository {
    private static final ParameterizedTypeReference<List<UserRepo>> userRepoTypeDef = new ParameterizedTypeReference<>() {
    };
    private static final ParameterizedTypeReference<List<RepoBranch>> repoBranchTypeDef = new ParameterizedTypeReference<>() {
    };
    private final WebClient webClient;

    GithubRepository(WebClient webClient) {
        this.webClient = webClient;
    }

    @Autowired
    public GithubRepository(@Value("${github.key}") String githubKey) {
        webClient = WebClient.builder()
                .baseUrl("https://api.github.com/")
                .defaultHeader("Accept", "application/vnd.github+json")
                .defaultHeader("Authorization", "Bearer " + githubKey)
                .defaultHeader("X-GitHub-Api-Version", "2022-11-28")
                .build();
    }

    public Mono<List<UserRepo>> getUserRepositories(String username) {
        return webClient.get()
                .uri("/users/{username}/repos", username)
                .exchangeToMono(mapNotFoundResponse(username));
    }

    public Mono<List<RepoBranch>> getUserRepositoryBranches(String username, String repositoryName) {
        return webClient.get()
                .uri("/repos/{username}/{repositoryName}/branches", username, repositoryName)
                .exchangeToMono(mapNotFoundUserRepoResponse(username, repositoryName));
    }

    private Function<ClientResponse, Mono<List<UserRepo>>> mapNotFoundResponse(String username) {
        return response -> {
            if (userDoesNotExist(response)) {
                return error(new UserNotFoundException(username));
            }
            return response.bodyToMono(userRepoTypeDef);
        };
    }

    private Function<ClientResponse, Mono<List<RepoBranch>>> mapNotFoundUserRepoResponse(String username, String repositoryName) {
        return response -> {
            if (userDoesNotExist(response)) {
                return error(new UserRepositoryNotFoundException(username, repositoryName));
            }
            return response.bodyToMono(repoBranchTypeDef);
        };
    }

    private boolean userDoesNotExist(ClientResponse rsp) {
        return rsp.statusCode() == HttpStatus.NOT_FOUND;
    }
}
