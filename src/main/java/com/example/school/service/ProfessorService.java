package com.example.school.service;

import com.example.school.model.Professor;
import com.example.school.repository.ProfessorRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.util.List;

@ApplicationScoped
public class ProfessorService {

    @Inject
    ProfessorRepository professorRepository;
    
    public List<Professor> listAll() {
        return professorRepository.listAll();
    }
    
    public Professor findById(Long id) {
        return professorRepository.findByIdOptional(id)
            .orElseThrow(() -> new NotFoundException("Professor não encontrado com id: " + id));
    }
    
    @Transactional
    public Professor create(Professor professor) {
        professorRepository.persist(professor);
        return professor;
    }
    
    @Transactional
    public Professor update(Long id, Professor professor) {
        Professor entity = findById(id);
        
        if (professor.getNome() != null) entity.setNome(professor.getNome());
        if (professor.getSobrenome() != null) entity.setSobrenome(professor.getSobrenome());
        if (professor.getIdade() != null) entity.setIdade(professor.getIdade());
        
        return entity;
    }
    
    @Transactional
    public void delete(Long id) {
        Professor professor = findById(id);
        professorRepository.delete(professor);
    }
    
    public List<Professor> findByNome(String nome) {
        return professorRepository.list("nome", nome);
    }
}