package eu.immontilla.currencyfair.markettradeprocessor.resources;

import io.dropwizard.auth.Auth;

import java.util.List;

import javax.validation.Valid;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import eu.immontilla.currencyfair.markettradeprocessor.model.BaseModel;
import eu.immontilla.currencyfair.markettradeprocessor.model.User;
import eu.immontilla.currencyfair.markettradeprocessor.resources.exception.InternalErrorException;
import eu.immontilla.currencyfair.markettradeprocessor.resources.exception.ResourceNotFoundException;

public interface BaseResource<T extends BaseModel> {

    @POST
    public Response create(@Auth User user, @Valid T model, @Context UriInfo info);

    @GET
    public List<T> list(@Auth User user) throws InternalErrorException;

    @GET
    @Path("/{id}")
    public T retrieve(@Auth User user, @PathParam("id") Long id) throws ResourceNotFoundException, InternalErrorException;

    @PUT
    @Path("/{id}")
    public T update(@Auth User user, @PathParam("id") Long id, @Valid T entity) throws ResourceNotFoundException, InternalErrorException;

    @DELETE
    @Path("/{id}")
    public Response delete(@Auth User user, @PathParam("id") Long id) throws ResourceNotFoundException, InternalErrorException;
}
