package pl.software2.service.githubchecker.services;

import org.springframework.stereotype.Component;
import pl.software2.service.githubchecker.model.Branch;
import pl.software2.service.githubchecker.model.Repository;
import pl.software2.service.githubchecker.model.github.RepoBranch;
import pl.software2.service.githubchecker.model.github.UserRepo;
import reactor.util.function.Tuple2;

import java.util.List;

@Component
public class GitHubToExternalModelMapper {

    public Repository map(Tuple2<UserRepo, List<RepoBranch>> tuple) {
        UserRepo repo = tuple.getT1();
        List<RepoBranch> branches = tuple.getT2();
        return new Repository(
                repo.getName(),
                repo.getOwner().getLogin(),
                branches.stream()
                        .map(this::mapBranch)
                        .toList()
        );
    }

    private Branch mapBranch(RepoBranch branch) {
        return new Branch(branch.getName(), branch.getCommit().getSha());
    }
}
