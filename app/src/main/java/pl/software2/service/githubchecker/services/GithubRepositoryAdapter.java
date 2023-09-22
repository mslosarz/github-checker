package pl.software2.service.githubchecker.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.software2.service.githubchecker.model.github.UserRepo;
import pl.software2.service.githubchecker.repositories.GithubRepository;
import reactor.core.publisher.Flux;

@Repository
public class GithubRepositoryAdapter {

    private final GithubRepository repository;

    @Autowired
    public GithubRepositoryAdapter(GithubRepository repository) {
        this.repository = repository;
    }

    public Flux<RepoBranchTuple> getUserRepositoryBranches(String username, UserRepo repo) {
        return repository.getUserRepositoryBranches(username, repo.getName())
                .flatMapMany(Flux::fromIterable)
                .map(branch -> new RepoBranchTuple(repo, branch));
    }

    public Flux<UserRepo> getUserRepositories(String username) {
        return repository.getUserRepositories(username)
                .flatMapMany(Flux::fromIterable);
    }
}
