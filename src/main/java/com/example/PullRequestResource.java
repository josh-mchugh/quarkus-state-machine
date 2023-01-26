package com.example;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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

        URI uri = UriBuilder.fromPath(String.format("/pull-request/%s/open", id )).build();

        return Response.created(uri).build();
    }

    @GET
    @Path("/{id}")
    public Response getPullRequest(@PathParam("id") Integer id) {

        return Response.ok(pullRequests.get(id)).build();
    }
}