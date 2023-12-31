package pl.software2.service.githubchecker.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class HcController {


    @Autowired
    private HcController() {
    }

    @GetMapping(value = "/hc", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<String> healthCheck() {
        return Mono.just("Ok");
    }
}
