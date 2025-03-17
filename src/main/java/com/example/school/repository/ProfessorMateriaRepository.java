package com.example.school.repository;

import com.example.school.model.ProfessorMateria;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class ProfessorMateriaRepository implements PanacheRepository<ProfessorMateria> {

    public List<ProfessorMateria> findByProfessorId(String identificador) {
        return find("professor.identificador", identificador).list();
    }

    public List<ProfessorMateria> findByMateriaId(String identificador) {
        return find("materia.identificador", identificador).list();
    }
}