package pl.software2.service.githubchecker.services;

import pl.software2.service.githubchecker.model.github.RepoBranch;
import pl.software2.service.githubchecker.model.github.UserRepo;

public record RepoBranchTuple(UserRepo repo, RepoBranch branch) {
}
