package pl.software2.service.githubchecker.repositories;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;
import pl.software2.service.githubchecker.model.github.BranchCommit;
import pl.software2.service.githubchecker.model.github.RepoBranch;
import pl.software2.service.githubchecker.model.github.RepoOwner;
import pl.software2.service.githubchecker.model.github.UserRepo;

import java.io.IOException;

import static java.util.Objects.requireNonNull;

@UtilityClass
class Fixtures {

    class Users {
        public static String withOneRepo(){
            return loadResource("github/users_single_repo.json");
        }

        public static String withTwoRepos(){
            return loadResource("github/users_two_repos.json");
        }

        public static String notFound(){
            return loadResource("github/user_not_found.json");
        }

        public static UserRepo expectedFirstRepository() {
            return UserRepo.builder()
                    .name("shade")
                    .fork(false)
                    .owner(RepoOwner.builder()
                            .login("dzik")
                            .build())
                    .build();
        }

        public static UserRepo expectedSecondRepository() {
            return UserRepo.builder()
                    .name("shade2")
                    .fork(false)
                    .owner(RepoOwner.builder()
                            .login("dzik")
                            .build())
                    .build();
        }
    }

    class Repos {
        public static String withSingleBranch(){
            return loadResource("github/repos_single_branch.json");
        }

        public static String withTwoBranches(){
            return loadResource("github/repos_two_branches.json");
        }

        public static RepoBranch expectedFirstBranch(){
            return RepoBranch.builder()
                    .name("master")
                    .commit(BranchCommit.builder()
                            .sha("c5b97d5ae6c19d5c5df71a34c7fbeeda2479ccbc")
                            .build())
                    .build();
        }

        public static RepoBranch expectedSecondBranch(){
            return RepoBranch.builder()
                    .name("test")
                    .commit(BranchCommit.builder()
                            .sha("c5co1ja1robie1tuu2uu1uuu5df71a34c7fbeeda2479ccbc")
                            .build())
                    .build();
        }
    }

    @NotNull
    private static String loadResource(String file) {
        try {
            return new String(requireNonNull(Fixtures.class.getClassLoader().getResourceAsStream(file)).readAllBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
