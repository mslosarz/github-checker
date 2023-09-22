package pl.software2.service.githubchecker.model.github;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRepo {

    private String name;
    private boolean fork;
    private RepoOwner owner;

}
