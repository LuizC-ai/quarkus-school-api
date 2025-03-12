package com.example.school.service;

import com.example.school.model.Aluno;
import com.example.school.model.Materia;
import com.example.school.model.Professor;
import com.example.school.repository.AlunoRepository;
import com.example.school.repository.MateriaRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class AlunoService {

    @Inject
    AlunoRepository alunoRepository;
    
    @Inject
    MateriaRepository materiaRepository;
    
    public List<Aluno> findAll() {
        return alunoRepository.listAll();
    }
    
    public Aluno findById(Long id) {
        return alunoRepository.findByIdOptional(id)
            .orElseThrow(() -> new NotFoundException("Aluno não encontrado com id: " + id));
    }
    
    @Transactional
    public Aluno create(Aluno aluno) {
        if (aluno.getAlunoMaterias() == null || aluno.getAlunoMaterias().isEmpty()) {
            throw new IllegalArgumentException("O aluno deve estar vinculado a pelo menos uma matéria");
        }
        
        alunoRepository.persist(aluno);
        return aluno;
    }
    
    @Transactional
    public Aluno update(Long id, Aluno aluno) {
        Aluno entity = findById(id);
        
        if (aluno.getNome() != null) entity.setNome(aluno.getNome());
        if (aluno.getSobrenome() != null) entity.setSobrenome(aluno.getSobrenome());
        if (aluno.getIdade() != null) entity.setIdade(aluno.getIdade());
        if (aluno.getTurma() != null) entity.setTurma(aluno.getTurma());
        
        return entity;
    }
    
    @Transactional
    public void delete(Long id) {
        boolean deleted = alunoRepository.deleteById(id);
        if (!deleted) {
            throw new NotFoundException("Aluno não encontrado com id: " + id);
        }
    }
    
    public List<Materia> findMateriasDoAluno(Long alunoId) {
        Aluno aluno = findById(alunoId);
        return aluno.getAlunoMaterias().stream()
            .map(am -> am.getMateria())
            .collect(Collectors.toList());
    }
    
    public List<Professor> findProfessoresDoAluno(Long alunoId) {
        Aluno aluno = findById(alunoId);
        return aluno.getAlunoMaterias().stream()
            .map(am -> am.getMateria().getProfessor())
            .distinct()
            .collect(Collectors.toList());
    }
    
    @Transactional
    public void matricularAlunoEmMateria(Long alunoId, Long materiaId) {
        Aluno aluno = findById(alunoId);
        Materia materia = materiaRepository.findByIdOptional(materiaId)
            .orElseThrow(() -> new NotFoundException("Materia não encontrada com id: " + materiaId));
        
        aluno.matricularEm(materia);
    }
}