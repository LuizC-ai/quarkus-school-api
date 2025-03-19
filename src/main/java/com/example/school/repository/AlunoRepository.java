package com.example.school.repository;

import com.example.school.model.Aluno;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class AlunoRepository implements PanacheRepository<Aluno> {

    public Optional<Aluno> findByIdentificador( String identificador ) {
        return find("identificador", identificador).singleResultOptional();
    }

    public boolean existsByIdentificador(String identificador) {
        return count("identificador", identificador) > 0;
    }

    public boolean deleteByIdentificador(String identificador) {
        return delete("identificador", identificador) > 0;
    }
}
