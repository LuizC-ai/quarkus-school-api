package com.example.school.resource;

import java.util.List;
import java.util.stream.Collectors;

import com.example.school.model.Aluno;
import com.example.school.model.Materia;
import com.example.school.model.Professor;
import com.example.school.repository.AlunoRepository;
import com.example.school.repository.MateriaRepository;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/alunos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AlunoResource {

    @Inject
    AlunoRepository alunoRepository;
    
    @Inject
    MateriaRepository materiaRepository;

    @GET
    public List<Aluno> getAll() {
        return alunoRepository.listAll();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        return alunoRepository.findByIdOptional(id)
                .map(aluno -> Response.ok(aluno).build())
                .orElse(Response.status(Status.NOT_FOUND).build());
    }

    @POST
    @Transactional
    public Response create(Aluno aluno) {
        // Validar se está vinculado a pelo menos uma matéria
        if (aluno.getMaterias() == null || aluno.getMaterias().isEmpty()) {
            return Response.status(Status.BAD_REQUEST)
                    .entity("O aluno deve estar vinculado a pelo menos uma matéria")
                    .build();
        }
        
        alunoRepository.persist(aluno);
        return Response.status(Status.CREATED).entity(aluno).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response update(@PathParam("id") Long id, Aluno aluno) {
        Aluno entity = alunoRepository.findById(id);
        if (entity == null) {
            return Response.status(Status.NOT_FOUND).build();
        }
        
        entity.setNome(aluno.getNome());
        entity.setSobrenome(aluno.getSobrenome());
        entity.setIdade(aluno.getIdade());
        entity.setTurma(aluno.getTurma());
        
        return Response.ok(entity).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response delete(@PathParam("id") Long id) {
        boolean deleted = alunoRepository.deleteById(id);
        return deleted ? Response.noContent().build() : Response.status(Status.NOT_FOUND).build();
    }
    
    @GET
    @Path("/{id}/materias")
    public Response getMateriasByAluno(@PathParam("id") Long id) {
        return alunoRepository.findByIdOptional(id)
                .map(aluno -> Response.ok(aluno.getMaterias()).build())
                .orElse(Response.status(Status.NOT_FOUND).build());
    }
    
    @GET
    @Path("/{id}/professores")
    public Response getProfessoresByAluno(@PathParam("id") Long id) {
        return alunoRepository.findByIdOptional(id)
                .map(aluno -> {
                    List<Professor> professores = aluno.getMaterias().stream()
                            .map(Materia::getProfessor)
                            .distinct()
                            .collect(Collectors.toList());
                    return Response.ok(professores).build();
                })
                .orElse(Response.status(Status.NOT_FOUND).build());
    }
    
    @PUT
    @Path("/{id}/materia/{materiaId}")
    @Transactional
    public Response matricularEmMateria(@PathParam("id") Long id, @PathParam("materiaId") Long materiaId) {
        Aluno aluno = alunoRepository.findById(id);
        Materia materia = materiaRepository.findById(materiaId);
        
        if (aluno == null || materia == null) {
            return Response.status(Status.NOT_FOUND).build();
        }
        
        aluno.getMaterias().add(materia);
        materia.getAlunos().add(aluno);
        
        return Response.ok(aluno).build();
    }
}
//CRIAR O SERVICE E PASSAR RESPONSABILIDADE PARA ELE
// MANTER RETORNO DEPOIS CRIAR O HANDLER DE EXCESSAO