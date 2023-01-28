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
    public void testPullRequestCreate() {

        given()
          .when().post("/pull-request/create")
          .then()
             .statusCode(Response.Status.CREATED.getStatusCode())
             .header("location", matchesPattern(".*/pull-request/\\d+"));
    }

    @Test
    public void testGetPullRequestById() {

        String pullrequestUrl = given()
            .when().post("/pull-request/create")
            .thenReturn()
            .header("location");

        given()
            .when().get(URI.create(pullrequestUrl).getPath())
            .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body(is("DRAFT"));
    }

}