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
    @Path("/{identificador}")
    public Response getById(@PathParam("identificador") String identificador) {
        AlunoDTO aluno = alunoService.findByIdentificador(identificador);
        return Response.ok(aluno).build();
    }

    @POST
    public Response create(@Valid AlunoDTO alunoDTO, @Context UriInfo uriInfo) {
        AlunoDTO created = alunoService.create(alunoDTO);
        URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(created.getIdentificador())).build();
        return Response.created(uri).entity(created).build();
    }

    @PUT
    @Path("/{identificador}")
    public Response update(@PathParam("identificador") String identificador, @Valid AlunoDTO alunoDTO) {
        AlunoDTO updated = alunoService.update(identificador, alunoDTO);
        return Response.ok(updated).build();
    }

    @DELETE
    @Path("/{identificador}")
    public Response delete(@PathParam("identificador") String identificador) {
        alunoService.delete(identificador);
        return Response.noContent().build();
    }
    
    @GET
    @Path("/{identificador}/materias")
    public Response getMateriasByAluno(@PathParam("identificador") String identificador) {
        List<MateriaDTO> materias = alunoService.getMateriasByAlunoIdentificador(identificador);
        return Response.ok(materias).build();
    }
    
    @GET
    @Path("/{identificador}/professores")
    public Response getProfessoresByAluno(@PathParam("identificador") String identificador) {
        List<ProfessorDTO> professores = alunoService.getProfessoresByAlunoIdentificador(identificador);
        return Response.ok(professores).build();
    }
    
    @PUT
    public Response matricularEmMateria(@QueryParam("identificador") String identificador, @QueryParam("materia-identificador") String materiaIdentificador) {
        alunoService.matricularEmMateria(identificador, materiaIdentificador);
        return Response.noContent().build();
    }
}
