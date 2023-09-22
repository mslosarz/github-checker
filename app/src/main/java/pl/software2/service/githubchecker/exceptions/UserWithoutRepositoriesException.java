package pl.software2.service.githubchecker.exceptions;

import org.springframework.http.HttpStatus;

public class UserWithoutRepositoriesException extends BusinessException {
    public UserWithoutRepositoriesException(String username) {
        super(HttpStatus.NOT_FOUND, username + " does not have any own repository");
    }
}
