package pl.software2.service.githubchecker.controllers;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import pl.software2.service.githubchecker.TestAppConfiguration;
import pl.software2.service.githubchecker.model.Repository;

import java.nio.charset.StandardCharsets;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static pl.software2.service.githubchecker.Fixtures.AppModel.expectedFirstRepositoryWithFirstBranch;
import static pl.software2.service.githubchecker.Fixtures.Repos.withSingleBranch;
import static pl.software2.service.githubchecker.Fixtures.Users.withOneRepo;

@ExtendWith(SpringExtension.class)
@WebFluxTest(UserController.class)
@Import(TestAppConfiguration.class)
class UserControllerTest {

    @Autowired
    private WebTestClient web;

    @Autowired
    private MockWebServer mockWebServer;

    @Test
    void shouldReturnResponse() {
        // given
        setupWebServerForOnerRepoAndOneBranch();

        // when & then
        web.get().uri("/user/mslosarz/repositories")
                .accept(MediaType.APPLICATION_JSON)
                .acceptCharset(StandardCharsets.UTF_8)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Repository.class)
                .contains(expectedFirstRepositoryWithFirstBranch());
    }

    private void setupWebServerForOnerRepoAndOneBranch() {
        MockResponse repos = new MockResponse()
                .addHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setBody(withOneRepo());
        MockResponse branches = new MockResponse()
                .addHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setBody(withSingleBranch());

        mockWebServer.enqueue(repos);
        mockWebServer.enqueue(branches);
    }

}