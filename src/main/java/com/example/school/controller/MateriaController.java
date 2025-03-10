package com.example.school.controller;

import com.example.school.model.Materia;
import com.example.school.service.MateriaService;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/materias")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MateriaController {

    private final MateriaService materiaService;

    public MateriaController(MateriaService materiaService) {

        this.materiaService = materiaService;
    }

    @GET
    public List<Materia> getAll() {
        return materiaService.findAll();
    }

    @GET
    @Path("/{id}")
    public Materia getById(@PathParam("id") Long id) {
        return materiaService.findById(id);
    }

    @POST
    public Response create(@Valid Materia materia) {
        Materia created = materiaService.create(materia);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @PUT
    @Path("/{id}")
    public Materia update(@PathParam("id") Long id, @Valid Materia materia) {
        return materiaService.update(id, materia);
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        materiaService.delete(id);
        return Response.noContent().build();
    }
}
