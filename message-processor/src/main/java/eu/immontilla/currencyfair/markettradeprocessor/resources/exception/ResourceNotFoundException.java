package eu.immontilla.currencyfair.markettradeprocessor.resources.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class ResourceNotFoundException extends WebApplicationException {

    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException() {
        super(Response.status(Response.Status.NOT_FOUND).build());
    }
}
