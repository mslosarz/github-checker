package pl.software2.service.githubchecker.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.software2.service.githubchecker.exceptions.UserWithoutRepositoriesException;
import pl.software2.service.githubchecker.model.Repository;
import pl.software2.service.githubchecker.model.github.UserRepo;
import pl.software2.service.githubchecker.repositories.GithubRepository;
import reactor.core.publisher.Flux;

import static reactor.core.publisher.Flux.error;
import static reactor.core.publisher.Mono.just;
import static reactor.core.publisher.Mono.zip;

@Component
public class UserService {

    private final GithubRepository githubRepository;
    private final GitHubToAppModelMapper mapper;

    @Autowired
    private UserService(GithubRepository githubRepository, GitHubToAppModelMapper mapper) {
        this.githubRepository = githubRepository;
        this.mapper = mapper;
    }

    public Flux<Repository> findUserRepositories(String username) {
        return githubRepository.getUserRepositories(username)
                .flatMapMany(Flux::fromIterable)
                .filter(this::isNotFork)
                .flatMap(repo -> zip(just(repo), githubRepository.getUserRepositoryBranches(username, repo.getName())))
                .map(mapper::map)
                .switchIfEmpty(error(new UserWithoutRepositoriesException(username)));

    }

    private boolean isNotFork(UserRepo repo) {
        return !repo.isFork();
    }
}
