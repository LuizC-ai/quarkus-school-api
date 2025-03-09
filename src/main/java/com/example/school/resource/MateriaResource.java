package com.example.school.resource;

import java.util.List;

import com.example.school.model.Materia;
import com.example.school.model.Professor;
import com.example.school.repository.MateriaRepository;
import com.example.school.repository.ProfessorRepository;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/materias")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MateriaResource {

    @Inject
    MateriaRepository materiaRepository;
    
    @Inject
    ProfessorRepository professorRepository;

    @GET
    public List<Materia> getAll() {
        return materiaRepository.listAll();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        return materiaRepository.findByIdOptional(id)
                .map(materia -> Response.ok(materia).build())
                .orElse(Response.status(Status.NOT_FOUND).build());
    }

    @POST
    @Transactional
    public Response create(Materia materia) {
        materiaRepository.persist(materia);
        return Response.status(Status.CREATED).entity(materia).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response update(@PathParam("id") Long id, Materia materia) {
        Materia entity = materiaRepository.findById(id);
        if (entity == null) {
            return Response.status(Status.NOT_FOUND).build();
        }
        
        entity.setNome(materia.getNome());
        entity.setQuantidadeHoras(materia.getQuantidadeHoras());
        
        return Response.ok(entity).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response delete(@PathParam("id") Long id) {
        boolean deleted = materiaRepository.deleteById(id);
        return deleted ? Response.noContent().build() : Response.status(Status.NOT_FOUND).build();
    }
    
    @PUT
    @Path("/{id}/professor/{professorId}")
    @Transactional
    public Response associarProfessor(@PathParam("id") Long id, @PathParam("professorId") Long professorId) {
        Materia materia = materiaRepository.findById(id);
        Professor professor = professorRepository.findById(professorId);
        
        if (materia == null || professor == null) {
            return Response.status(Status.NOT_FOUND).build();
        }
        
        materia.setProfessor(professor);
        professor.getMaterias().add(materia);
        
        return Response.ok(materia).build();
    }
}
