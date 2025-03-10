package com.example.school.repository;

import com.example.school.model.Materia;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MateriaRepository implements PanacheRepository<Materia> {
    // Métodos específicos podem ser adicionados aqui
}
