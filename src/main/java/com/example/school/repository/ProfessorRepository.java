package com.example.school.repository;

import com.example.school.model.Professor;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;


@ApplicationScoped
public class ProfessorRepository implements PanacheRepository<Professor> {

    public Optional<Professor> findByIdentificador( String identificador) {
        return find("identificador", identificador).firstResultOptional();
    }

    public List<Professor> findByNome( String nome) {
        return list("nome", nome);
    }

    public boolean existsByIdentificador(String identificador) {
        return count("identificador", identificador) > 0;
    }
    public boolean deleteByIdentificador(String identificador) {
        return delete("identificador", identificador) > 0;
    }
}
