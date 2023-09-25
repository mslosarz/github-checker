package pl.software2.service.githubchecker.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import pl.software2.service.githubchecker.TestAppConfiguration;

import java.nio.charset.StandardCharsets;

@ExtendWith(SpringExtension.class)
@WebFluxTest(HcController.class)
@Import(TestAppConfiguration.class)
class HcControllerTest {

    @Autowired
    private WebTestClient web;

    @Test
    void itsOk() {
        web.get().uri("/hc")
                .accept(MediaType.APPLICATION_JSON)
                .acceptCharset(StandardCharsets.UTF_8)
                .exchange()
                .expectStatus()
                .isOk();
    }
}