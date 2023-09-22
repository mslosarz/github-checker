package pl.software2.service.githubchecker.services;

import org.junit.jupiter.api.Test;
import reactor.util.function.Tuples;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.software2.service.githubchecker.Fixtures.AppModel.expectedFirstRepositoryWithFirstBranch;
import static pl.software2.service.githubchecker.Fixtures.Repos.expectedFirstBranch;
import static pl.software2.service.githubchecker.Fixtures.Users.expectedFirstRepository;

class GitHubToExternalModelMapperTest {

    private final GitHubToAppModelMapper mapper = new GitHubToAppModelMapper();

    @Test
    void shouldMapGithubModelToAppModel(){
        // given
        var tuple = Tuples.of(expectedFirstRepository(), List.of(expectedFirstBranch()));

        // when
        var result = mapper.map(tuple);

        // then
        assertThat(result).isEqualTo(expectedFirstRepositoryWithFirstBranch());
    }

}