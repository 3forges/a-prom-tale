package com.example;

import java.net.URI;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

@Path("fruit")
@RequestScoped
public class FruitResource {
    @Inject
    private FruitService fruitService;

    @SuppressWarnings("unused")
  private final long id = 0;

/**
 * Get Fruit by name
 * The [lagInMillisec] query param will allow us to test our monitoring
 */
  @GET
  @Path("/{name}")
  @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
  public Response get(@PathParam("name") String name, @QueryParam("lagInMillisec") Integer lagInMillisec) {
    Fruit fruit;

    // ...
    try {
      Thread.sleep((lagInMillisec != null &&  lagInMillisec >= 0 ?lagInMillisec:5000));
    } catch (InterruptedException e) {
      System.err.println("An InterruptedException occured: " + e.getMessage());
      Thread.currentThread().interrupt();
    }

    // return Response.ok(fruit).build();
    return Response.ok(this.fruitService.getFruitsByName(name)).build();
  }
/**
 * Get Fruit by name
 */
  @GET
  @Path("/id/{id}")
  @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
  public Response getFruitById(@PathParam("id") String fruitId) {
    Fruit fruit;

    // ...

    // return Response.Response.ok(fruit).build();
    return Response.ok(this.fruitService.getFruitsById(fruitId)).build();
  }
  
  @GET
  @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
  public Response getAllFruits() {
    int paginationItemsPerPage = 7;
    // ...
    // return Response.ok(this.fruitService.getAllFruits(paginationItemsPerPage)).build();
    return Response.ok(this.fruitService.getSomeFakeFruits(paginationItemsPerPage)).build();
  }



  @PUT
  @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
  @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
  public Response create(Fruit fruit, @Context UriInfo uriInfo) {
    // Fruit fruit;

    // ... on sauvegarde la représentation du fruit

    Fruit createdFruit = this.fruitService.createFruit(fruit);
    // on construit l'URI correspondant à la personne
    URI location = uriInfo.getRequestUriBuilder()
                          .path(createdFruit.getName())
                          .build();
    return Response.ok(fruit).build();
    // return Response.created(location).entity(createdFruit).build();
  }

/**
 * Get Fruit by name
 */
  @DELETE
  @Path("/{id}")
  @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
  public Response deleteById(@PathParam("id") String fruitId) {
    Fruit fruit;

    // ... should instead delete the fruit from DB

    // return Response.Response.ok(fruit).build();
    return Response.ok(this.fruitService.deleteFruitById(fruitId)).build();
  }
/*
  @POST
  @Path("/subscription")
  public void subscribe() {
    //....
  }

  @GET
  @Path("/subscription/{idSubscription}")
  @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
  public Subscription getSubscription(@PathParam("idSubscription") String idSub) {
    //....
  }
*/


}