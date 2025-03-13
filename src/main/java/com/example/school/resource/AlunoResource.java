package com.example.school.resource;

import com.example.school.dto.AlunoDTO;
import com.example.school.dto.MateriaDTO;
import com.example.school.dto.ProfessorDTO;
import com.example.school.service.AlunoService;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.core.Context;

import java.net.URI;
import java.util.List;

@Path("/alunos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AlunoResource {

    @Inject
    AlunoService alunoService;
    
    @GET
    public Response getAll() {
        List<AlunoDTO> alunos = alunoService.findAll();
        return Response.ok(alunos).build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        AlunoDTO aluno = alunoService.findById(id);
        return Response.ok(aluno).build();
    }

    @POST
    public Response create(@Valid AlunoDTO alunoDTO, @Context UriInfo uriInfo) {
        AlunoDTO created = alunoService.create(alunoDTO);
        URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(created.getId())).build();
        return Response.created(uri).entity(created).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, @Valid AlunoDTO alunoDTO) {
        AlunoDTO updated = alunoService.update(id, alunoDTO);
        return Response.ok(updated).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        alunoService.delete(id);
        return Response.noContent().build();
    }
    
    @GET
    @Path("/{id}/materias")
    public Response getMateriasByAluno(@PathParam("id") Long id) {
        List<MateriaDTO> materias = alunoService.getMateriasByAluno(id);
        return Response.ok(materias).build();
    }
    
    @GET
    @Path("/{id}/professores")
    public Response getProfessoresByAluno(@PathParam("id") Long id) {
        List<ProfessorDTO> professores = alunoService.getProfessoresByAluno(id);
        return Response.ok(professores).build();
    }
    
    @PUT
    @Path("/{id}/materia/{materiaId}")
    public Response matricularEmMateria(@PathParam("id") Long id, @PathParam("materiaId") Long materiaId) {
        AlunoDTO aluno = alunoService.matricularEmMateria(id, materiaId);
        return Response.ok(aluno).build();
    }
}
