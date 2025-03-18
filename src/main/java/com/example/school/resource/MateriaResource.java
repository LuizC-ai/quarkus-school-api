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
    @Path("/{identificador}")
    public Response getById(@PathParam("identificador") String identificador) {
        MateriaDTO materia = materiaService.findByIdentificador(identificador);
        return Response.ok(materia).build();
    }

    @POST
    public Response create(@Valid MateriaDTO materiaDTO, @Context UriInfo uriInfo) {
        MateriaDTO created = materiaService.create(materiaDTO);
        URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(created.getIdentificador())).build();
        return Response.created(uri).entity(created).build();
    }

    @PUT
    @Path("/{identificador}")
    public Response update(@PathParam("identificador") String identificador, @Valid MateriaDTO materiaDTO) {
        MateriaDTO updated = materiaService.update(identificador, materiaDTO);
        return Response.ok(updated).build();
    }

    @DELETE
    @Path("/{identificador}")
    public Response delete(@PathParam("identificador") String identificador) {
        materiaService.delete(identificador);
        return Response.noContent().build();
    }
    
    @PUT
    public Response associarProfessor(@QueryParam("identificador") String identificador, @QueryParam("professor-identificador") String professorIdentificador) {
        MateriaDTO materia = materiaService.associarProfessor(identificador, professorIdentificador);
        return Response.ok(materia).build();
    }
}
