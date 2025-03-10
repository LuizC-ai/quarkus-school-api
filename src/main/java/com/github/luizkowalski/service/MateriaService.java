package com.github.luizkowalski.service;

import com.github.luizkowalski.domain.Materia;
import com.github.luizkowalski.repository.MateriaRepository;
import com.github.luizkowalski.util.QueryManager;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

import java.util.List;

@ApplicationScoped
public class MateriaService {

    @Inject
    MateriaRepository materiaRepository;

    public Materia findById(Long id) {
        return materiaRepository.findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("Materia não encontrada com id: " + id));
    }

    public List<Materia> findAll() {
        return materiaRepository.listAll();
    }
    
    public QueryManager.PagedResult<Materia> findAllPaged(int page, Integer size) {
        QueryManager<Materia> queryManager = new QueryManager<>();
        return queryManager.getPagedResult(materiaRepository.findAll(), page, size);
    }

    public Materia create(Materia materia) {
        materiaRepository.persist(materia);
        return materia;
    }

    public void delete(Long id) {
        materiaRepository.deleteById(id);
    }
}
