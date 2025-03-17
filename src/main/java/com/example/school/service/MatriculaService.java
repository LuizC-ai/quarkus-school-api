package com.example.school.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;


@ApplicationScoped
public class MatriculaService {

    @Inject
    EntityManager entityManager;

    @Transactional
    public void desassociarAlunoMateria(Long alunoId, Long materiaId) {
        entityManager.createNativeQuery("DELETE FROM aluno_materia WHERE aluno_id = :aluno AND materia_id = :materia")
            .setParameter("aluno", alunoId)
            .setParameter("materia", materiaId)
            .executeUpdate();
    } 

    public boolean isAlunoMatriculado(Long alunoId, Long materiaId) {
        Long count = (Long) entityManager.createNativeQuery(
            "SELECT COUNT(1) FROM aluno_materia WHERE aluno_id = :aluno AND materia_id = :materia")
            .setParameter("aluno", alunoId)
            .setParameter("materia", materiaId)
            .getSingleResult();

            return count > 0;
    }
}
