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

        Integer pullRequestId = createPullRequest();

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

        Integer pullRequestId = createPullRequest();

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

    private Integer createPullRequest() {

        String pullrequestUrl = given()
            .when().post("/pull-request/create")
            .thenReturn()
            .header("location");

        String path = URI.create(pullrequestUrl).getPath();

        String[] pathSegments = path.substring(0).split("/pull-request/");

        return Integer.valueOf(pathSegments[1]);
    }
}