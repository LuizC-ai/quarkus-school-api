package com.example.school.exception;

import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Throwable> {

    @Context
    UriInfo uriInfo;

    @Override
    public Response toResponse(Throwable exception) {
        ErrorResponse errorResponse = new ErrorResponse();
        
        if (exception instanceof ResourceNotFoundException || exception instanceof NotFoundException) {
            errorResponse.setStatus(Response.Status.NOT_FOUND.getStatusCode());
            errorResponse.setError("Not Found");
        } else if (exception instanceof WebApplicationException) {
            WebApplicationException webEx = (WebApplicationException) exception;
            errorResponse.setStatus(webEx.getResponse().getStatus());
            errorResponse.setError("Application Error");
        } else {
            errorResponse.setStatus(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
            errorResponse.setError("Internal Server Error");
        }
        
        errorResponse.setMessage(exception.getMessage());
        errorResponse.setPath(uriInfo.getPath());
        
        return Response.status(errorResponse.getStatus())
                .entity(errorResponse)
                .build();
    }
}
