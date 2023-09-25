package pl.software2.service.githubchecker.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.software2.service.githubchecker.model.Repository;
import pl.software2.service.githubchecker.services.UserService;
import reactor.core.publisher.Flux;

@RestController
public class UserController {

    private final UserService service;

    @Autowired
    private UserController(UserService service) {
        this.service = service;
    }

    @GetMapping(value = "/user/{username}/repositories", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Repository> getUserRepositories(@PathVariable String username) {
        return service.findUserRepositories(username);
    }
}
