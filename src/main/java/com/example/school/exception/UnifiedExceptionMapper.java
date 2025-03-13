package com.example.school.exception;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import jakarta.validation.ValidationException;

import java.time.LocalDateTime;

@Provider
public class UnifiedExceptionMapper implements ExceptionMapper<Throwable> {

    @Context
    UriInfo uriInfo;

    @Override
    public Response toResponse(Throwable exception) {
        ErrorResponse errorResponse = new ErrorResponse();
        
        // Configurar campos padrão
        errorResponse.setPath(uriInfo != null ? uriInfo.getPath() : "");
        errorResponse.setMessage(exception.getMessage());
        
        // Determinar o tipo de exceção e configurar o status e código de acordo
        if (exception instanceof ResourceNotFoundException) {
            errorResponse.setStatus(Response.Status.NOT_FOUND.getStatusCode());
            errorResponse.setCode("RESOURCE_NOT_FOUND");
            errorResponse.setError("Not Found");
        } else if (exception instanceof ValidationException) {
            errorResponse.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            errorResponse.setCode("VALIDATION_ERROR");
            errorResponse.setError("Bad Request");
        } else if (exception instanceof WebApplicationException) {
            WebApplicationException webEx = (WebApplicationException) exception;
            errorResponse.setStatus(webEx.getResponse().getStatus());
            errorResponse.setCode("API_ERROR");
            errorResponse.setError("Application Error");
        } else {
            // Para exceções desconhecidas ou não tratadas
            errorResponse.setStatus(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
            errorResponse.setCode("INTERNAL_ERROR");
            errorResponse.setError("Internal Server Error");
            
            // Log para exceções inesperadas
            System.err.println("Exceção não tratada: " + exception.getClass().getName());
            exception.printStackTrace();
        }
        
        // Construir e retornar a resposta com o status HTTP apropriado
        return Response.status(errorResponse.getStatus())
                .entity(errorResponse)
                .build();
    }
}
