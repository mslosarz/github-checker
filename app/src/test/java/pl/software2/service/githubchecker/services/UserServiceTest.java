package pl.software2.service.githubchecker.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.software2.service.githubchecker.exceptions.UserWithoutRepositoriesException;
import pl.software2.service.githubchecker.model.Repository;
import pl.software2.service.githubchecker.repositories.GithubRepository;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.Mockito.when;
import static pl.software2.service.githubchecker.Fixtures.Repos.expectedFirstBranch;
import static pl.software2.service.githubchecker.Fixtures.Users.expectedFirstRepository;
import static pl.software2.service.githubchecker.Fixtures.Users.expectedForkRepository;
import static reactor.core.publisher.Mono.just;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private GithubRepository repository;

    @Spy
    private GitHubToAppModelMapper mapper = new GitHubToAppModelMapper();

    @Test
    public void shouldPerformCallForRepositoriesAndForBranches() {
        // given
        when(repository.getUserRepositories("dzik")).thenReturn(just(List.of(expectedFirstRepository())));
        when(repository.getUserRepositoryBranches("dzik", "shade")).thenReturn(just(List.of(expectedFirstBranch())));

        // when
        Flux<Repository> result = userService.findUserRepositories("dzik");

        // then
        StepVerifier
                .create(result)
                .expectNextMatches(repos -> repos.repositoryName().equals("shade") && repos.branches().size() == 1)
                .expectComplete()
                .verify();
    }

    @Test
    public void shouldThrowNotFoundWhenAllRepositoriesAreForks() {
        // given
        when(repository.getUserRepositories("dzik")).thenReturn(just(List.of(expectedForkRepository())));

        // when
        Flux<Repository> result = userService.findUserRepositories("dzik");

        // then
        StepVerifier
                .create(result)
                .expectErrorMatches(t -> t instanceof UserWithoutRepositoriesException)
                .verify();
    }

}