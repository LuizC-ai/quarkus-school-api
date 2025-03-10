package com.example.school.exception;

import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {
    
    @Override
    public Response toResponse(NotFoundException exception) {
        ErrorResponse error = new ErrorResponse(
                "RESOURCE_NOT_FOUND", 
                exception.getMessage());
        
        return Response.status(Response.Status.NOT_FOUND)
                .entity(error)
                .build();
    }
}