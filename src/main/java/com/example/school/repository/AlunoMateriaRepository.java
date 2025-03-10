package com.example.school.repository;

import com.example.school.model.AlunoMateria;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class AlunoMateriaRepository implements PanacheRepository<AlunoMateria> {
    
    public List<AlunoMateria> findByAlunoId(Long alunoId) {
        return list("alunoId", alunoId);
    }
    
    public List<AlunoMateria> findByMateriaId(Long materiaId) {
        return list("materiaId", materiaId);
    }
}
