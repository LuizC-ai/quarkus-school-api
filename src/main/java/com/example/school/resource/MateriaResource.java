package com.example.school.resource;

import com.example.school.dto.MateriaDTO;
import com.example.school.service.MateriaService;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.net.URI;
import java.util.List;

@Path("/materias")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MateriaResource {

    @Inject
    MateriaService materiaService;

    @GET
    public Response getAll() {
        List<MateriaDTO> materias = materiaService.findAll();
        return Response.ok(materias).build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        MateriaDTO materia = materiaService.findById(id);
        return Response.ok(materia).build();
    }

    @POST
    public Response create(@Valid MateriaDTO materiaDTO, @Context UriInfo uriInfo) {
        MateriaDTO created = materiaService.create(materiaDTO);
        URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(created.getId())).build();
        return Response.created(uri).entity(created).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, @Valid MateriaDTO materiaDTO) {
        MateriaDTO updated = materiaService.update(id, materiaDTO);
        return Response.ok(updated).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        materiaService.delete(id);
        return Response.noContent().build();
    }
    
    @PUT
    @Path("/{id}/professor/{professorId}")
    public Response associarProfessor(@PathParam("id") Long id, @PathParam("professorId") Long professorId) {
        MateriaDTO materia = materiaService.associarProfessor(id, professorId);
        return Response.ok(materia).build();
    }
}
