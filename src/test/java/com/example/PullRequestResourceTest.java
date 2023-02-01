package com.example;

import io.quarkus.test.junit.QuarkusTest;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.matchesPattern;

import java.net.URI;

import javax.ws.rs.core.Response;

@QuarkusTest
public class PullRequestResourceTest {

    @Test
    public void whenCreatePulleRquestThenResponse() {

        given()
          .when()
            .post("/pull-request/create")
          .then()
             .statusCode(Response.Status.CREATED.getStatusCode())
             .header("location", matchesPattern(".*/pull-request/\\d+"));
    }

    @Test
    public void whenGetPullRequestByIdThenResponse() {

        Integer pullRequestId = createDraftPullRequest();

        given()
            .when()
                .get(String.format("/pull-request/%s", pullRequestId))
            .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body(is("DRAFT"));
    }

    @Test
    public void whenGetPullRequestByIdDoesNotExistsThenExpectNotFound() {

        given()
            .when()
                .get(String.format("/pull-request/%s", Integer.MIN_VALUE))
            .then()
                .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    public void whenPullRequestSetToOpenFromDraftThenExpectResponse() {

        Integer pullRequestId = createDraftPullRequest();

        given()
            .when()
                .post(String.format("/pull-request/%s/open", pullRequestId))
            .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body(is("OPEN"));
                
    }

    @Test
    public void whenPullRequestSetOpenAndPullRequestIdDoesNotExistsThenExpectNotFound() {

        given()
            .when()
                .post(String.format("/pull-request/%s/open", Integer.MIN_VALUE))
            .then()
                .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    public void whenPullRequestSetToMergeFromDraftThenExpectBadResponse() {

        Integer pullRequestId = createDraftPullRequest();

        given()
            .when()
                .post(String.format("/pull-request/%s/merge", pullRequestId))
            .then()
                .statusCode(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void whenPullRequestSetToClosedFromDraftThenExpectResponse() {

        Integer pullRequestId = createDraftPullRequest();

        given()
            .when()
                .post(String.format("/pull-request/%s/close", pullRequestId))
            .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body(is("CLOSED"));
    }

    @Test
    public void whenPullRequestSetToMergeAndPullRequestIdDoesNotExistsThenExpectNotFound() {

        given()
            .when()
                .post(String.format("/pull-request/%s/merge", Integer.MIN_VALUE))
            .then()
                .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    public void whenPullRequestSetToMergeFromOpenThenExpectResponse() {

        Integer pullRequestId = createOpenPullRequest();

        given()
            .when()
                .post(String.format("/pull-request/%s/merge", pullRequestId))
            .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body(is("MERGED"));
    }

    @Test
    public void whenPullRequestSetClosedFromMergeThenExpectBadRequest() {

        Integer pullRequestId = createMegeredPullRequest();

        given()
            .when()
                .post(String.format("/pull-request/%s/open", pullRequestId))
            .then()
                .statusCode(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void whenPullRequestSetToMergeWhenPullRequestIdIsInvalidThenExpectNotFound() {

        given()
            .when()
                .post(String.format("/pull-request/%s/merge", Integer.MIN_VALUE))
            .then()
                .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    public void whenPullRequestSetToClosedFromOpenThenExpectResponse() {

        Integer pullRequestId = createOpenPullRequest();

        given()
            .when()
                .post(String.format("/pull-request/%s/close", pullRequestId))
            .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body(is("CLOSED"));
    }

    @Test
    public void whenPullRequestSetToOpenFromClosedthenExpectResponse() {

        Integer pullRequestId = createClosedPullRequest();

        given()
            .when()
                .post(String.format("/pull-request/%s/open", pullRequestId))
            .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body(is("OPEN"));
    }

    @Test
    public void whenPullRequestSetToClosedWhenPullRequestIdDoesNotExistThenExpectNotFound() {

        given()
            .when()
                .post(String.format("/pull-request/%s/open", Integer.MIN_VALUE))
            .then()
                .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }

    private Integer createDraftPullRequest() {

        String pullrequestUrl = given()
            .when().post("/pull-request/create")
            .thenReturn()
            .header("location");

        String path = URI.create(pullrequestUrl).getPath();

        String[] pathSegments = path.substring(0).split("/pull-request/");

        return Integer.valueOf(pathSegments[1]);
    }
    
    private Integer createOpenPullRequest() {

        Integer pullRequestId = createDraftPullRequest();

        given()
            .when()
                .post(String.format("/pull-request/%s/open", pullRequestId))
            .thenReturn();

        return pullRequestId;
    }

    private Integer createMegeredPullRequest() {

        Integer pullRequestId = createOpenPullRequest();

        given()
            .when()
                .post(String.format("/pull-request/%s/merge", pullRequestId))
            .thenReturn();

        return pullRequestId;
    }

    private Integer createClosedPullRequest() {

        Integer pullRequestId = createOpenPullRequest();

        given()
            .when()
                .post(String.format("/pull-request/%s/close", pullRequestId))
            .thenReturn();

        return pullRequestId;
    }
}