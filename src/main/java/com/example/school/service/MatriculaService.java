package com.example.school.service;

import com.example.school.exception.ResourceNotFoundException;
import com.example.school.model.Aluno;
import com.example.school.model.AlunoMateria;
import com.example.school.model.Materia;
import com.example.school.repository.AlunoMateriaRepository;
import com.example.school.repository.AlunoRepository;
import com.example.school.repository.MateriaRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.Optional;

@ApplicationScoped
public class MatriculaService {

    @Inject
    AlunoRepository alunoRepository;

    @Inject
    MateriaRepository materiaRepository;

    @Inject
    AlunoMateriaRepository alunoMateriaRepository;


    @Transactional
    public void desassociarAlunoMateria(String alunoIdentificador, String materiaIdentificador) {
        verificarAlunoExiste(alunoIdentificador);
        verificarMateriaExiste(materiaIdentificador);
        Optional<AlunoMateria> matricula = alunoMateriaRepository.find(
                "aluno.identificador = ?1 AND materia.identificador = ?2",
                alunoIdentificador, materiaIdentificador).firstResultOptional();
        if (matricula.isPresent()) {
            alunoMateriaRepository.delete(matricula.get());
        }
    }


    public boolean isAlunoMatriculado(String alunoIdentificador, String materiaIdentificador) {
        verificarAlunoExiste(alunoIdentificador);
        verificarMateriaExiste(materiaIdentificador);

        long count = alunoMateriaRepository.count(
                "aluno.identificador = ?1 AND materia.identificador = ?2",
                alunoIdentificador, materiaIdentificador);

        return count > 0;
    }


    @Transactional
    public void matricular(String alunoIdentificador, String materiaIdentificador) {
        Aluno aluno = alunoRepository.findByIdentificador( alunoIdentificador)
                .orElseThrow(() -> new ResourceNotFoundException("Aluno não encontrado com identificador: " + alunoIdentificador));

        Materia materia = materiaRepository.findByIdentificador( materiaIdentificador)
                .orElseThrow(() -> new ResourceNotFoundException("Matéria não encontrada com identificador: " + materiaIdentificador));

        boolean jaMatriculado = isAlunoMatriculado(alunoIdentificador, materiaIdentificador);

        if (!jaMatriculado) {
            AlunoMateria alunoMateria = new AlunoMateria();
            alunoMateria.setAluno(aluno);
            alunoMateria.setMateria(materia);
            alunoMateriaRepository.persist(alunoMateria);
        }
    }


    private void verificarAlunoExiste(String identificador) {
        boolean existe = alunoRepository.count("identificador", identificador) > 0;
        if (!existe) {
            throw new ResourceNotFoundException("Aluno não encontrado com identificador: " + identificador);
        }
    }

    private void verificarMateriaExiste(String identificador) {
        boolean existe = materiaRepository.count("identificador", identificador) > 0;
        if (!existe) {
            throw new ResourceNotFoundException("Matéria não encontrada com identificador: " + identificador);
        }
    }
}