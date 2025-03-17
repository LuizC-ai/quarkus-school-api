package com.example.school.repository;

import com.example.school.model.ProfessorMateria;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ProfessorMateriaRepository implements PanacheRepository<ProfessorMateria> {

    public List<ProfessorMateria> findByProfessorIdentificador(String professorIdentificador) {
        return find("professor.identificador", professorIdentificador).list();
    }

    public List<ProfessorMateria> findByMateriaIdentificador(String materiaIdentificador) {
        return find("materia.identificador", materiaIdentificador).list();
    }

    public Optional<ProfessorMateria> findByProfessorAndMateriaIdentificador(
            String professorIdentificador, String materiaIdentificador) {
        return find("professor.identificador = ?1 AND materia.identificador = ?2",
                professorIdentificador, materiaIdentificador).firstResultOptional();
    }

    public boolean existsByProfessorAndMateriaIdentificador(
            String professorIdentificador, String materiaIdentificador) {
        return count("professor.identificador = ?1 AND materia.identificador = ?2",
                professorIdentificador, materiaIdentificador) > 0;
    }
}