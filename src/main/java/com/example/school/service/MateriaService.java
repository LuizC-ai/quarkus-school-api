package com.example.school.service;

import java.util.List;
import java.util.Objects;

import com.example.school.dto.MateriaDTO;
import com.example.school.exception.ResourceNotFoundException;
import com.example.school.mapper.MateriaMapper;
import com.example.school.model.Materia;
import com.example.school.model.Professor;
import com.example.school.repository.MateriaRepository;
import com.example.school.repository.ProfessorRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class MateriaService {

    @Inject
    MateriaRepository materiaRepository;
    
    @Inject
    ProfessorRepository professorRepository;
    
    @Inject
    MateriaMapper mapper;
    
    public List<MateriaDTO> findAll() {
        List<Materia> materias = materiaRepository.listAll();
        return mapper.toDTOList(materias);
    }
    
    public MateriaDTO findById(Long id) {
        Materia materia = findEntityById(id);
        return mapper.toDTO(materia);
    }
    
    @Transactional
    public MateriaDTO create(MateriaDTO materiaDTO) {
        Objects.requireNonNull(materiaDTO, "Materia não pode ser nula");
        
        if (materiaDTO.getId() != null) {
            materiaDTO.setId(null); // Forçar criação de novo registro
        }
        
        Materia materia = mapper.toEntity(materiaDTO);
        materiaRepository.persist(materia);
        return mapper.toDTO(materia);
    }
    
    @Transactional
    public MateriaDTO update(Long id, MateriaDTO materiaDTO) {
        Objects.requireNonNull(materiaDTO, "Materia não pode ser nula");
        Objects.requireNonNull(id, "ID não pode ser nulo");
        
        Materia entity = findEntityById(id);
        mapper.updateEntityFromDTO(materiaDTO, entity);
        
        return mapper.toDTO(entity);
    }
    
    @Transactional
    public void delete(Long id) {
        boolean deleted = materiaRepository.deleteById(id);
        if (!deleted) {
            throw new ResourceNotFoundException("Materia não encontrada com id: " + id);
        }
    }
    
    @Transactional
    public MateriaDTO associarProfessor(Long materiaId, Long professorId) {
        Materia materia = findEntityById(materiaId);
        
        Professor professor = professorRepository.findByIdOptional(professorId)
            .orElseThrow(() -> new ResourceNotFoundException("Professor não encontrado com id: " + professorId));
        
        materia.setProfessor(professor);
        
        return mapper.toDTO(materia);
    }
    
    /**
     * Método auxiliar para recuperar entidade por ID ou lançar exceção
     */
    private Materia findEntityById(Long id) {
        return materiaRepository.findByIdOptional(id)
            .orElseThrow(() -> new ResourceNotFoundException("Materia não encontrada com id: " + id));
    }
}
