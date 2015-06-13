package eu.immontilla.currencyfair.markettradeprocessor.resources.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class InternalErrorException extends WebApplicationException {
    private static final long serialVersionUID = 1L;

    public InternalErrorException() {
        super(Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
    }
}
