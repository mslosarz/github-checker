package pl.software2.service.githubchecker.exceptions;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends BusinessException {


    public UserNotFoundException(String username) {
        super(HttpStatus.NOT_FOUND, username + " user was not found");
    }
}
