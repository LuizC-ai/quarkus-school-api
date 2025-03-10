package com.example.school.service;

import com.example.school.exception.ResourceNotFoundException;
import com.example.school.model.Materia;
import com.example.school.repository.MateriaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;


import java.util.List;

/**
 * Service responsible for handling Materia business operations
 */
@ApplicationScoped
public class MateriaService {
    
    private final MateriaRepository materiaRepository;
    
    @Inject
    public MateriaService( MateriaRepository materiaRepository) {
        this.materiaRepository = materiaRepository;
    }
    
    /**
     * Retrieves all Materia entities
     * @return List of all Materia entities
     */
    public List<Materia> findAll() {
        return materiaRepository.listAll();
    }
    
    /**
     * Finds a Materia by its ID
     * @param id Materia ID
     * @return Materia entity
     * @throws ResourceNotFoundException if Materia not found
     */
    public Materia findById(Long id) {
        return materiaRepository.findByIdOptional(id)
            .orElseThrow(() -> new ResourceNotFoundException("Materia not found with id: " + id));
    }
    
    /**
     * Creates a new Materia
     * @param materia Materia to be created
     * @return Created Materia
     */
    @Transactional
    public Materia create(Materia materia) {
        materiaRepository.persist(materia);
        return materia;
    }
    
    /**
     * Updates an existing Materia
     * @param id Materia ID
     * @param materia Updated Materia data
     * @return Updated Materia
     * @throws ResourceNotFoundException if Materia not found
     */
    @Transactional
    public Materia update(Long id, Materia materia) {
        Materia entity = findById(id);
        entity.setName(materia.getName());
        // Update other properties as needed
        return entity;
    }
    
    /**
     * Deletes a Materia by its ID
     * @param id Materia ID
     * @throws ResourceNotFoundException if Materia not found
     */
    @Transactional
    public void delete(Long id) {
        Materia entity = findById(id);
        materiaRepository.delete(entity);
    }
}
