package com.example.school.controller;

import com.example.school.model.Aluno;
import com.example.school.service.AlunoService;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/alunos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AlunoController {

    private final AlunoService alunoService;

    public AlunoController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    @GET
    public List<Aluno> getAll() {
        return alunoService.findAll();
    }

    @GET
    @Path("/{id}")
    public Aluno getById(@PathParam("id") Long id) {
        return alunoService.findById(id);
    }

    @POST
    public Response create(@Valid Aluno aluno) {
        Aluno created = alunoService.create(aluno);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @PUT
    @Path("/{id}")
    public Aluno update(@PathParam("id") Long id, @Valid Aluno aluno) {
        return alunoService.update(id, aluno);
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        alunoService.delete(id);
        return Response.noContent().build();
    }
}
