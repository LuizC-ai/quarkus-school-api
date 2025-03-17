package com.example.school.repository;

import com.example.school.model.AlunoMateria;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class AlunoMateriaRepository implements PanacheRepository<AlunoMateria> {

    public List<AlunoMateria> findByAlunoIdentificador(String alunoIdentificador) {
        return find("aluno.identificador", alunoIdentificador).list();
    }

    public List<AlunoMateria> findByMateriaIdentificador(String materiaIdentificador) {
        return find("materia.identificador", materiaIdentificador).list();
    }

    public Optional<AlunoMateria> findByAlunoAndMateriaIdentificador(
            String alunoIdentificador, String materiaIdentificador) {
        return find("aluno.identificador = ?1 AND materia.identificador = ?2",
                alunoIdentificador, materiaIdentificador).firstResultOptional();
    }

    public boolean existsByAlunoAndMateriaIdentificador(
            String alunoIdentificador, String materiaIdentificador) {
        return count("aluno.identificador = ?1 AND materia.identificador = ?2",
                alunoIdentificador, materiaIdentificador) > 0;
    }
}
