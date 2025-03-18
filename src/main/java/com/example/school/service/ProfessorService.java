package com.example.school.service;

import com.example.school.dto.ProfessorDTO;
import com.example.school.exception.ResourceNotFoundException;
import com.example.school.mapper.ProfessorMapper;
import com.example.school.model.Professor;
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
        Objects.requireNonNull(identificador, "ID não pode ser nulo");
        
        Professor entity = professorRepository.findByIdentificador(identificador)
                        .orElseThrow(() -> new ResourceNotFoundException("Professor não encontrado com identificador: " + identificador));
        mapper.updateEntityFromDTO(professorDTO, entity);
        
        return mapper.toDTO(entity);
    }
    
    @Transactional
    public void delete(String identificador) {
        if(!professorRepository.existsByIdentificador(identificador)) {
            throw new ResourceNotFoundException("Professor não encontrado com identificador: " + identificador);
        }
        professorRepository.deleteByIdentificador(identificador);
    }

    public List<ProfessorDTO> findByNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new BadRequestException("Parâmetro 'nome' é obrigatório");
        }
        
        return mapper.toDTOList(professorRepository.findByNome(nome));
    }

    private Optional<Professor> findEntityByIdentificador( String identificador) {
        return Optional.ofNullable( professorRepository.findByIdentificador( identificador )
                .orElseThrow( ( ) -> new ResourceNotFoundException( "Professor não encontrado com id: " + identificador ) ) );
    }
}