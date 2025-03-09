package com.example.school.rest;

import java.util.List;
import java.util.stream.Collectors;

import com.example.school.dto.ProfessorDTO;
import com.example.school.mapper.ProfessorMapper;
import com.example.school.model.Professor;
import com.example.school.repository.ProfessorRepository;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/professores")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Professor", description = "Operações relacionadas a professores")
public class ProfessorResource {

    @Inject
    ProfessorRepository repository;
    
    @Inject
    ProfessorMapper professorMapper;

    @GET
    @Operation(summary = "Listar todos os professores")
    public List<ProfessorDTO> listarProfessores() {
        return repository.listAll().stream()
                .map(professor -> professorMapper.toDTO(professor))
                .collect(Collectors.toList());
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Buscar um professor pelo ID")
    public Response buscarProfessor(@PathParam("id") Long id) {
        Professor professor = repository.findById(id);
        if (professor == null) {
            return Response.status(Status.NOT_FOUND).entity("Professor nao encontrado").build();
        }
        return Response.ok(professorMapper.toDTO(professor)).build();
    }
    
    @POST
    @Transactional
    @Operation(summary = "Criar um novo professor")
    public Response criarProfessor(ProfessorDTO professorDTO) {
        Professor professor = professorMapper.toEntity(professorDTO);
        repository.persist(professor);
        return Response.status(Status.CREATED)
                        .entity(professorMapper.toDTO(professor))
                        .build();
    }
    
    @PUT
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Atualizar um professor existente")
    public Response atualizarProfessor(@PathParam("id") Long id, ProfessorDTO professorDTO) {
        Professor professor = repository.findById(id);
        if (professor == null) {
            return Response.status(Status.NOT_FOUND).entity("Professor nao encontrado").build();
        }
        
        professor.setNome(professorDTO.getNome());
        professor.setSobrenome(professorDTO.getSobrenome());
        professor.setIdade(professorDTO.getIdade());
        
        return Response.ok(professorMapper.toDTO(professor)).build();
    }
    
    @DELETE
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Remover um professor")
    public Response excluirProfessor(@PathParam("id") Long id) {
        Professor professor = repository.findById(id);
        if (professor == null) {
            return Response.status(Status.NOT_FOUND).entity("Professor nao encontrado").build();
        }
        
        repository.delete(professor);
        return Response.noContent().build();
    }
}
