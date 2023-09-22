package pl.software2.service.githubchecker.model;

import java.util.List;

public record Repository(String repositoryName, String ownerLogin, List<Branch> branches) {
}
