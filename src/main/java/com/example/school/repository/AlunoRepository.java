package com.example.school.repository;

import com.example.school.model.Aluno;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AlunoRepository implements PanacheRepository<Aluno> {
    // Os métodos básicos são herdados do PanacheRepository
}
