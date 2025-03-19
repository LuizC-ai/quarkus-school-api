package com.example.school.service;

import com.example.school.dto.ProfessorDTO;
import com.example.school.exception.ResourceNotFoundException;
import com.example.school.mapper.ProfessorMapper;
import com.example.school.model.Professor;
import com.example.school.model.ProfessorMateria;
import com.example.school.repository.ProfessorMateriaRepository;
import com.example.school.repository.ProfessorRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@ApplicationScoped
public class ProfessorService {

    @Inject
    ProfessorRepository professorRepository;
    
    @Inject
    ProfessorMapper mapper;

    @Inject
    ProfessorMateriaRepository professorMateriaRepository;

    public List<ProfessorDTO> findAll() {
        return mapper.toDTOList(professorRepository.listAll());
    }
    
    public ProfessorDTO findByIdentificador(String identificador) {
        Professor professor = professorRepository.findByIdentificador(identificador)
                .orElseThrow(() -> new ResourceNotFoundException("Professor não encontrado com identificador: " + identificador));
        return mapper.toDTO(professor);
    }
    
    @Transactional
    public ProfessorDTO create(ProfessorDTO professorDTO) {
        Objects.requireNonNull(professorDTO, "Professor não pode ser nulo");

        
        Professor professor = mapper.toEntity(professorDTO);
        professorRepository.persist(professor);
        return mapper.toDTO(professor);
    }
    
    @Transactional
    public ProfessorDTO update(String identificador, ProfessorDTO professorDTO) {
        Objects.requireNonNull(professorDTO, "Professor não pode ser nulo");
        Objects.requireNonNull(identificador, "Identificador não pode ser nulo");

        Professor entity = findEntityByIdentificador (identificador);

        mapper.updateEntityFromDTO(professorDTO, entity);
        
        return mapper.toDTO(entity);
    }

    //nao vai funcionar/
    @Transactional
    public void delete(String identificador) {
        List<ProfessorMateria> relacoes = professorMateriaRepository.findByProfessorIdentificador(identificador);
        for (ProfessorMateria relacao : relacoes) {
            professorMateriaRepository.delete(relacao);
        }
        Professor professor = findEntityByIdentificador(identificador);
        professorRepository.delete(professor);
    }

    public List<ProfessorDTO> findByNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new BadRequestException("Parâmetro 'nome' é obrigatório");
        }
        
        return mapper.toDTOList(professorRepository.findByNome(nome));
    }

    private Professor findEntityByIdentificador(String identificador) {
        return professorRepository.findByIdentificador(identificador)
                .orElseThrow(() -> new ResourceNotFoundException("Professor não encontrado com identificador: " + identificador));
    }
}