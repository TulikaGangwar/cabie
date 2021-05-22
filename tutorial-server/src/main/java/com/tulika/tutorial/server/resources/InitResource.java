package com.tulika.tutorial.server.resources;

import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.tulika.tutorial.core.BaseClient;
import com.tulika.tutorial.model.Person;
import com.tulika.tutorial.model.requests.SaveRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
@Api("Init Resource")
@Produces(MediaType.APPLICATION_JSON)
@Singleton
@Slf4j
public class InitResource {
    private final BaseClient client;

    @Inject
    public InitResource(@Named("redis") BaseClient client) {

        this.client = client;
    }

    @GET
    @Path("/tulika")
    @ApiOperation("Testing...")
    public Response nameCanBeAnything() {
        log.info("tulika is the best");
        Person p= new Person("tulika","2");
        Person p2= Person.builder()
                .name("sajal")
                .id("3")
                .build();

        return Response.ok()//ok function sets response status code to 200 and return a response builder class
                .entity("Helloo World")
                .build();
    }

    @POST
    @Path("/save")
    @ApiOperation("saving.....")
    public Response save(final SaveRequest saveRequest){
        String saveResult= client.put(saveRequest.getKey(),saveRequest.getValue());
        return Response.ok()
                .entity(saveRequest)
                .build();


    }

    @GET
    @Path("/get/{key}")
    @ApiOperation("getting.....")
    public Response get(@PathParam("key") final String key){
        String saveResult= client.get(key);
        return Response.ok()
                .entity(saveResult)
                .build();


    }



}
