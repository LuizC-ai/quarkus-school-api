package com.example.school.exception;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class WebApplicationExceptionMapper implements ExceptionMapper<WebApplicationException> {
    
    @Override
    public Response toResponse(WebApplicationException exception) {
        ErrorResponse error = new ErrorResponse(
                "API_ERROR", 
                exception.getMessage());
        
        return Response.status(exception.getResponse().getStatus())
                .entity(error)
                .build();
    }
}