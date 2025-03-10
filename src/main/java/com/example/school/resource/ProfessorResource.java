package com.example.school.resource;

import com.example.school.model.Professor;
import com.example.school.service.ProfessorService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;


import java.net.URI;
import java.util.List;

@Path("/professors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProfessorResource {
    
    @Inject
    ProfessorService professorService;
    
    @GET
    public List<Professor> getAllProfessors() {
        return professorService.findAll();
    }
    
    @GET
    @Path("/{id}")
    public Professor getProfessor(@PathParam("id") Long id) {
        return professorService.findById(id);
    }
    
    @POST
    public Response createProfessor(Professor professor, @Context UriInfo uriInfo) {
        Professor created = professorService.create(professor);
        URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(created.getId())).build();
        return Response.created(uri).entity(created).build();
    }
    
    @PUT
    @Path("/{id}")
    public Professor updateProfessor(@PathParam("id") Long id, Professor professor) {
        return professorService.update(id, professor);
    }
    
    @DELETE
    @Path("/{id}")
    public Response deleteProfessor(@PathParam("id") Long id) {
        professorService.delete(id);
        return Response.noContent().build();
    }
}
