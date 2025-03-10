package com.example.school.service;

import com.example.school.model.Aluno;
import com.example.school.repository.AlunoRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.util.List;

@ApplicationScoped
public class AlunoService {

    @Inject
    AlunoRepository alunoRepository;
    
    public List<Aluno> listAll() {
        return alunoRepository.listAll();
    }
    
    public Aluno findById(Long id) {
        return alunoRepository.findByIdOptional(id)
            .orElseThrow(() -> new NotFoundException("Aluno não encontrado com id: " + id));
    }
    
    @Transactional
    public Aluno create(Aluno aluno) {
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
        Aluno aluno = findById(id);
        alunoRepository.delete(aluno);
    }

    public List<Aluno> findAll( ) {
        return alunoRepository.listAll();
    }
}