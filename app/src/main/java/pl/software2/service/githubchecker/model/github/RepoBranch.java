package pl.software2.service.githubchecker.model.github;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RepoBranch {
    private String name;
    private BranchCommit commit;
}
