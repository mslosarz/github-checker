package pl.software2.service.githubchecker.exceptions;

import org.springframework.http.HttpStatus;

public class UserRepositoryNotFoundException extends BusinessException {
    public UserRepositoryNotFoundException(String username, String repositoryName) {
        super(HttpStatus.NOT_FOUND, username + " user does not have " + repositoryName + " repository");
    }
}
