package eu.immontilla.currencyfair.markettradeprocessor.resources;

import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;

import java.net.URI;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;

import eu.immontilla.currencyfair.markettradeprocessor.configuration.AppConfiguration;
import eu.immontilla.currencyfair.markettradeprocessor.facade.BaseFacade;
import eu.immontilla.currencyfair.markettradeprocessor.model.Message;
import eu.immontilla.currencyfair.markettradeprocessor.model.SerializedModel;
import eu.immontilla.currencyfair.markettradeprocessor.model.User;
import eu.immontilla.currencyfair.markettradeprocessor.resources.exception.InternalErrorException;
import eu.immontilla.currencyfair.markettradeprocessor.resources.exception.ResourceNotFoundException;

@Path("/messages")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MessageResource implements BaseResource<Message> {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageResource.class);

    private final BaseFacade<Message> facade;
    private final URI resourceUri;
    @SuppressWarnings("unused")
    private final AppConfiguration config;

    @Inject
    public MessageResource(BaseFacade<Message> facade, AppConfiguration config) throws java.net.URISyntaxException {
        this.resourceUri = config.getEndpoint().resolve("/messages");
        this.facade = facade;
        this.config = config;
    }

    @Override
    @POST
    @UnitOfWork
    public Response create(@Auth User user, @Valid Message model, @Context UriInfo info) {
        LOGGER.info("Creating trade message with admin user {}", user.getUsername());
        if (model.getTimePlaced() == null) model.setTimePlaced(new Date());
        Message obj = facade.create(model);
        URI resource = HttpUtils.getCreatedResourceURI(info, resourceUri, obj.getId());
        LOGGER.info("Trade message with id {} created", obj.getId());
        return Response.created(resource).entity(new SerializedModel<>("message", obj)).build();
    }

    @Override
    @GET
    @UnitOfWork
    public List<Message> list(@Auth User user) throws InternalErrorException {
        LOGGER.info("Getting trade message list' with admin user {}", user.getUsername());
        return facade.findByParams(Optional.fromNullable(null));
    }

    @GET
    @Override
    @Path("/{id}")
    @UnitOfWork
    public Message retrieve(@Auth User user, @PathParam("id") Long id) throws ResourceNotFoundException, InternalErrorException {
        LOGGER.info("Retrieving trade message {} with admin user {}", id, user.getUsername());
        Optional<Message> op = facade.findById(id);
        if (!op.isPresent()) {
            throw new ResourceNotFoundException();
        }
        return op.get();
    }

    @PUT
    @Override
    @Path("/{id}")
    @UnitOfWork
    public Message update(@Auth User user, @PathParam("id") Long id, @Valid Message model) throws ResourceNotFoundException, InternalErrorException {
        LOGGER.info("Updating trade message {} with admin user {}", id, user.getUsername());
        Optional<Message> op = facade.findById(id);
        if (!op.isPresent()) {
            throw new ResourceNotFoundException();
        }
        return facade.update(model);
    }

    @DELETE
    @Override
    @Path("/{id}")
    @UnitOfWork
    public Response delete(@Auth User user, @PathParam("id") Long id) throws ResourceNotFoundException, InternalErrorException {
        LOGGER.info("Deleting trade message {} with admin user {}", id, user.getUsername());
        Optional<Message> op = facade.findById(id);
        if (!op.isPresent()) {
            throw new ResourceNotFoundException();
        }
        Message obj = op.get();
        facade.delete(obj);
        return Response.ok().build();
    }

}
