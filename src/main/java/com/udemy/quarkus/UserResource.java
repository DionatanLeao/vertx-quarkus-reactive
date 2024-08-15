package com.udemy.quarkus;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.panache.common.Sort;
import io.smallrye.mutiny.Uni;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@Path("/users")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class UserResource {
    private static final Logger LOG = LoggerFactory.getLogger(UserResource.class);

    @GET
    public Uni<List<User>> get() {
        LOG.info("Get all users...");
        return User.listAll(Sort.by("id"));
    }

    @GET
    @Path("/{id}")
    public Uni<User> getById(Long id) {
        LOG.info("Get by id: {}", id);
        return User.findById(id);
    }

    @POST
    public void create(User user) {
        LOG.info("Create: {}", user);
        Panache.<User>withTransaction(user::persist)
        .onItem().transform(insertedUser ->
            Response.created(URI.create("/users/" + insertedUser.id)).build()
        );
    }
}
