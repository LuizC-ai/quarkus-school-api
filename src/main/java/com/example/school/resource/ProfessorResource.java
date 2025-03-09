package com.example.school.resource;

import java.util.List;

import com.example.school.model.Materia;
import com.example.school.model.Professor;
import com.example.school.repository.ProfessorRepository;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/professores")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProfessorResource {

    @Inject
    ProfessorRepository professorRepository;

    @GET
    public List<Professor> getAll() {
        return professorRepository.listAll();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        return professorRepository.findByIdOptional(id)
                .map(professor -> Response.ok(professor).build())
                .orElse(Response.status(Status.NOT_FOUND).build());
    }

    @POST
    @Transactional
    public Response create(Professor professor) {
        professorRepository.persist(professor);
        return Response.status(Status.CREATED).entity(professor).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response update(@PathParam("id") Long id, Professor professor) {
        Professor entity = professorRepository.findById(id);
        if (entity == null) {
            return Response.status(Status.NOT_FOUND).build();
        }
        
        entity.setNome(professor.getNome());
        entity.setSobrenome(professor.getSobrenome());
        entity.setIdade(professor.getIdade());
        
        return Response.ok(entity).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response delete(@PathParam("id") Long id) {
        boolean deleted = professorRepository.deleteById(id);
        return deleted ? Response.noContent().build() : Response.status(Status.NOT_FOUND).build();
    }
    
    @GET
    @Path("/{id}/materias")
    public Response getMateriasByProfessor(@PathParam("id") Long id) {
        return professorRepository.findByIdOptional(id)
                .map(professor -> Response.ok(professor.getMaterias()).build())
                .orElse(Response.status(Status.NOT_FOUND).build());
    }
}
