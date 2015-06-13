package eu.immontilla.currencyfair.markettradeprocessor.resources.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class BadRequestException extends WebApplicationException {
    private static final long serialVersionUID = 1L;

    public BadRequestException() {
        super(Response.status(Response.Status.BAD_REQUEST).build());
    }
}
