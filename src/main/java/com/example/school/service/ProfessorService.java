package com.example.school.service;

import com.example.school.dto.ProfessorDTO;
import com.example.school.exception.ResourceNotFoundException;
import com.example.school.mapper.EntityMapper;
import com.example.school.model.Professor;
import com.example.school.repository.ProfessorRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;

import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class ProfessorService {

    @Inject
    ProfessorRepository professorRepository;
    
    @Inject
    EntityMapper entityMapper;
    
    public List<ProfessorDTO> findAll() {
        return entityMapper.toProfessorDTOList(professorRepository.listAll());
    }
    
    public ProfessorDTO findById(Long id) {
        Professor professor = findEntityById(id);
        return entityMapper.toProfessorDTO(professor);
    }
    
    @Transactional
    public ProfessorDTO create(ProfessorDTO professorDTO) {
        Objects.requireNonNull(professorDTO, "Professor não pode ser nulo");
        
        if (professorDTO.getId() != null) {
            professorDTO.setId(null); // Forçar criação de novo registro
        }
        
        Professor professor = entityMapper.toProfessorEntity(professorDTO);
        professorRepository.persist(professor);
        return entityMapper.toProfessorDTO(professor);
    }
    
    @Transactional
    public ProfessorDTO update(Long id, ProfessorDTO professorDTO) {
        Objects.requireNonNull(professorDTO, "Professor não pode ser nulo");
        Objects.requireNonNull(id, "ID não pode ser nulo");
        
        Professor entity = findEntityById(id);
        entityMapper.updateProfessorFromDto(professorDTO, entity);
        
        return entityMapper.toProfessorDTO(entity);
    }
    
    @Transactional
    public void delete(Long id) {
        Professor professor = findEntityById(id);
        professorRepository.delete(professor);
    }
    
    public List<ProfessorDTO> findByNome(String nome) {
        // Adicionada validação que estava no resource
        if (nome == null || nome.trim().isEmpty()) {
            throw new BadRequestException("Parâmetro 'nome' é obrigatório");
        }
        
        return entityMapper.toProfessorDTOList(professorRepository.list("nome", nome));
    }
    
    /**
     * Método auxiliar para recuperar entidade por ID ou lançar exceção
     */
    private Professor findEntityById(Long id) {
        return professorRepository.findByIdOptional(id)
            .orElseThrow(() -> new ResourceNotFoundException("Professor não encontrado com id: " + id));
    }
}