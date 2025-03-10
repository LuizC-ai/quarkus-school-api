package com.example.school.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Provider
public class ExceptionMapper implements jakarta.ws.rs.ext.ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception exception) {
        Map<String, Object> errorInfo = new HashMap<>();
        
        int status = Response.Status.INTERNAL_SERVER_ERROR.getStatusCode();
        
        if (exception instanceof ResourceNotFoundException) {
            status = Response.Status.NOT_FOUND.getStatusCode();
        } else if (exception instanceof jakarta.validation.ValidationException) {
            status = Response.Status.BAD_REQUEST.getStatusCode();
        } else if (exception instanceof jakarta.ws.rs.WebApplicationException) {
            status = ((jakarta.ws.rs.WebApplicationException) exception).getResponse().getStatus();
        }
        
        errorInfo.put("timestamp", LocalDateTime.now().toString());
        errorInfo.put("status", status);
        errorInfo.put("message", exception.getMessage());
        
        return Response.status(status)
                .entity(errorInfo)
                .build();
    }
}
