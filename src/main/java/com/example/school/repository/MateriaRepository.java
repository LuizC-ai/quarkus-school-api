package com.example.school.repository;

import com.example.school.model.Materia;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class MateriaRepository implements PanacheRepository<Materia> {

    public Optional<Materia> findByIdentificador( String identificador) {
        return find("identificador", identificador).firstResultOptional();
    }

    public boolean existsByIdentificador(String identificador) {
        return count("identificador", identificador) > 0;
    }

    public boolean deleteByIdentificador(String identificador) {
        return delete("identificador", identificador) > 0;
    }
}
