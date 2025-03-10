package com.example.school.resource;

import java.util.List;

import com.example.school.model.Professor;
import com.example.school.service.ProfessorService;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/professores")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProfessorResource {

    @Inject
    ProfessorService professorService;

    @GET
    public List<Professor> getAll() {
        return professorService.listAll();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        Professor professor = professorService.findById(id);
        return Response.ok(professor).build();
    }

    @POST
    public Response create(Professor professor) {
        Professor created = professorService.create(professor);
        return Response.status(Status.CREATED).entity(created).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, Professor professor) {
        Professor updated = professorService.update(id, professor);
        return Response.ok(updated).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        professorService.delete(id);
        return Response.noContent().build();
    }
    
    @GET
    @Path("/{id}/materias")
    public Response getMateriasByProfessor(@PathParam("id") Long id) {
        Professor professor = professorService.findById(id);
        return Response.ok(professor.getMaterias()).build();
    }
}

