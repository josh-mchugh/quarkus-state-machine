package com.example;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

@Path("/pull-request")
public class PullRequestResource {

    private AtomicInteger counter = new AtomicInteger();
    private Map<Integer, PullRequestState> pullRequests = new ConcurrentHashMap<>();

    @POST
    @Path("/create")
    public Response createPullResponse() {

        Integer id = counter.incrementAndGet();
        pullRequests.put(id, PullRequestState.DRAFT);

        URI uri = UriBuilder.fromPath(String.format("/pull-request/%s", id )).build();

        return Response.created(uri).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getPullRequest(@PathParam("id") Integer id) {

        if(!pullRequests.containsKey(id)) {

            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(pullRequests.get(id)).build();
    }

    @POST
    @Path("/{id}/open")
    @Produces(MediaType.TEXT_PLAIN)
    public Response postPullRequestOpen(@PathParam("id") Integer id, @PathParam("state") String state) {

        return handlePullRequest(id, PullRequestState.OPEN);
    }

    @POST
    @Path("/{id}/merge")
    @Produces(MediaType.TEXT_PLAIN)
    public Response postPullRequestMerge(@PathParam("id") Integer id) {

        return handlePullRequest(id, PullRequestState.MERGED);
    }

    @POST
    @Path("/{id}/close")
    @Produces(MediaType.TEXT_PLAIN)
    public Response postPullRequestClose(@PathParam("id") Integer id) {

        return handlePullRequest(id, PullRequestState.CLOSED);
    }

    private Response handlePullRequest(Integer id, PullRequestState nextState) {

        if(!pullRequests.containsKey(id)) {

            return Response.status(Response.Status.NOT_FOUND).build();
        }

        PullRequestState currentState = pullRequests.get(id);

        if(!currentState.canProccedToNextState(nextState)) {

            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        pullRequests.put(id, nextState);

        return Response.ok(pullRequests.get(id)).build();
    }
}