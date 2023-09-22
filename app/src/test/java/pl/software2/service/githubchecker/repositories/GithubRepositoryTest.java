package pl.software2.service.githubchecker.repositories;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import pl.software2.service.githubchecker.exceptions.BusinessException;
import reactor.test.StepVerifier;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static pl.software2.service.githubchecker.Fixtures.Repos.*;
import static pl.software2.service.githubchecker.Fixtures.Users.*;

class GithubRepositoryTest {

    private final MockWebServer mockWebServer = new MockWebServer();

    @Test
    void shouldFetchSingleRepo() throws InterruptedException {
        // given
        expectedResponse(withOneRepo());

        // when
        var result = repository().getUserRepositories("dzik");

        // then
        StepVerifier
                .create(result)
                .expectNextMatches(repos -> checkIfResponseHasValidLengthAndEntries(repos, expectedFirstRepository()))
                .expectComplete()
                .verify();
        assertThat(mockWebServer.takeRequest().getPath()).isEqualTo("/users/dzik/repos");
    }

    @Test
    void shouldFetchMultipleRepos() {
        // given
        expectedResponse(withTwoRepos());

        // when
        var result = repository().getUserRepositories("dzik");

        // then
        StepVerifier
                .create(result)
                .expectNextMatches(repos -> checkIfResponseHasValidLengthAndEntries(repos, expectedFirstRepository(), expectedSecondRepository()))
                .expectComplete()
                .verify();
    }

    @Test
    void shouldThrowNotFoundUserException() {
        // given
        MockResponse mockResponse = new MockResponse()
                .addHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setResponseCode(404)
                .setBody(notFound());
        mockWebServer.enqueue(mockResponse);

        // when
        var result = repository().getUserRepositories("dzik");

        // then
        StepVerifier
                .create(result)
                .expectErrorMatches(exception -> checkExceptionTypeAndMessage(exception, "dzik"))
                .verify();
    }

    @Test
    void shouldFetchRepoWithSingleBranch() throws InterruptedException {
        // given
        expectedResponse(withSingleBranch());

        // when
        var result = repository().getUserRepositoryBranches("dzik", "ryje");

        // then
        StepVerifier
                .create(result)
                .expectNextMatches(branches -> checkIfResponseHasValidLengthAndEntries(branches, expectedFirstBranch()))
                .expectComplete()
                .verify();
        assertThat(mockWebServer.takeRequest().getPath()).isEqualTo("/repos/dzik/ryje/branches");
    }

    @Test
    void shouldFetchRepoWithTwoBranches() throws InterruptedException {
        // given
        expectedResponse(withTwoBranches());

        // when
        var result = repository().getUserRepositoryBranches("zajac", "skaczepo");

        // then
        StepVerifier
                .create(result)
                .expectNextMatches(branches -> checkIfResponseHasValidLengthAndEntries(branches, expectedFirstBranch(), expectedSecondBranch()))
                .expectComplete()
                .verify();
        assertThat(mockWebServer.takeRequest().getPath()).isEqualTo("/repos/zajac/skaczepo/branches");
    }

    @Test
    void shouldThrowNotFoundUserRepoException() {
        // given
        MockResponse mockResponse = new MockResponse()
                .addHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setResponseCode(404)
                .setBody(notFound());
        mockWebServer.enqueue(mockResponse);

        // when
        var result = repository().getUserRepositoryBranches("dzik", "skaczepo");

        // then
        StepVerifier
                .create(result)
                .expectErrorMatches(exception -> checkExceptionTypeAndMessage(exception, "dzik user does not have skaczepo"))
                .verify();
    }

    private boolean checkExceptionTypeAndMessage(Throwable t, String messagePart) {
        return t instanceof BusinessException
                && t.getMessage().contains(messagePart);
    }

    private void expectedResponse(String body) {
        MockResponse mockResponse = new MockResponse()
                .addHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setBody(body);
        mockWebServer.enqueue(mockResponse);
    }

    private <T> boolean checkIfResponseHasValidLengthAndEntries(List<T> repos, T... expected) {
        return repos.size() == expected.length
                && repos.containsAll(List.of(expected));
    }

    private GithubRepository repository() {
        return new GithubRepository(WebClient.builder().baseUrl(mockWebServer.url("/").toString()));
    }
}