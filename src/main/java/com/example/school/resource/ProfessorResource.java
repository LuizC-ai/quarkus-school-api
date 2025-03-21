package com.example.school.resource;

import com.example.school.dto.ProfessorDTO;
import com.example.school.service.ProfessorService;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
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
    public Response getAllProfessors() {
        List<ProfessorDTO> professors = professorService.findAll();
        return Response.ok(professors).build();
    }
    
    @GET
    @Path("/{identificador}")
    public Response getProfessor(@PathParam("identificador") String identificador) {
        ProfessorDTO professor = professorService.findByIdentificador(identificador);
        return Response.ok(professor).build();
    }
    
    @POST
    public Response createProfessor(@Valid ProfessorDTO professorDTO, @Context UriInfo uriInfo) {
        ProfessorDTO created = professorService.create(professorDTO);
        URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(created.getIdentificador())).build();
        return Response.created(uri).entity(created).build();
    }
    
    @PUT
    @Path("/{identificador}")
    public Response updateProfessor(@PathParam("identificador") String identificador, @Valid ProfessorDTO professorDTO) {
        ProfessorDTO updated = professorService.update(identificador, professorDTO);
        return Response.ok(updated).build();
    }
    
    @DELETE
    @Path("/{identificador}")
    public Response deleteProfessor(@PathParam("identificador") String identificador) {
        professorService.delete(identificador);
        return Response.noContent().build();
    }
    
    @GET
    @Path("/search")
    public Response findByNome(@QueryParam("nome") String nome) {
        List<ProfessorDTO> professors = professorService.findByNome(nome);
        return Response.ok(professors).build();
    }
}
