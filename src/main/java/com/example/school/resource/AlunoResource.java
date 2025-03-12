package com.example.school.resource;

import java.util.List;
import java.util.stream.Collectors;

import com.example.school.dto.AlunoDTO;
import com.example.school.dto.MateriaDTO;
import com.example.school.model.Aluno;
import com.example.school.service.AlunoService;
import com.example.school.mapper.AlunoMapper;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/alunos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AlunoResource {

    @Inject
    AlunoService alunoService;
    
    @Inject
    AlunoMapper alunoMapper; // Precisaríamos criar esta classe de mapper
    
    @GET
    public List<AlunoDTO> getAll() {
        return alunoService.listAll().stream()
            .map(alunoMapper::toDTO)
            .collect(Collectors.toList());
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        try {
            Aluno aluno = alunoService.findById(id);
            return Response.ok(alunoMapper.toDTO(aluno)).build();
        } catch (NotFoundException e) {
            return Response.status(Status.NOT_FOUND).build();
        }
    }

    @POST
    public Response create(AlunoDTO alunoDTO) {
        try {
            Aluno aluno = alunoMapper.toEntity(alunoDTO);
            Aluno created = alunoService.create(aluno);
            return Response.status(Status.CREATED)
                    .entity(alunoMapper.toDTO(created))
                    .build();
        } catch (BadRequestException e) {
            return Response.status(Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, AlunoDTO alunoDTO) {
        try {
            Aluno aluno = alunoMapper.toEntity(alunoDTO);
            Aluno updated = alunoService.update(id, aluno);
            return Response.ok(alunoMapper.toDTO(updated)).build();
        } catch (NotFoundException e) {
            return Response.status(Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        try {
            alunoService.delete(id);
            return Response.noContent().build();
        } catch (NotFoundException e) {
            return Response.status(Status.NOT_FOUND).build();
        }
    }
    
    @GET
    @Path("/{id}/materias")
    public Response getMateriasByAluno(@PathParam("id") Long id) {
        try {
            List<MateriaDTO> materias = alunoService.getMateriasByAluno(id);
            return Response.ok(materias).build();
        } catch (NotFoundException e) {
            return Response.status(Status.NOT_FOUND).build();
        }
    }
    
    @GET
    @Path("/{id}/professores")
    public Response getProfessoresByAluno(@PathParam("id") Long id) {
        try {
            List<ProfessorDTO> professores = alunoService.getProfessoresByAluno(id);
            return Response.ok(professores).build();
        } catch (NotFoundException e) {
            return Response.status(Status.NOT_FOUND).build();
        }
    }
    
    @PUT
    @Path("/{id}/materia/{materiaId}")
    public Response matricularEmMateria(@PathParam("id") Long id, @PathParam("materiaId") Long materiaId) {
        try {
            Aluno aluno = alunoService.matricularEmMateria(id, materiaId);
            return Response.ok(alunoMapper.toDTO(aluno)).build();
        } catch (NotFoundException e) {
            return Response.status(Status.NOT_FOUND).build();
        }
    }
}
