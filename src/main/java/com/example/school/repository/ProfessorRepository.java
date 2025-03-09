package com.example.school.repository;

import com.example.school.model.Professor;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

/*
 * A interface PanacheRepository é uma interface que fornece métodos CRUD
 * Ao usar Professor, estamos dizendo que a interface PanacheRepository
 * irá trabalhar com a entidade Professor. Isto significa que,
 * a interface PanacheRepository irá fornecer métodos CRUD para a entidade Professor.
 * O CRUD é um acrônimo para Create, Read, Update e Delete.
 * Estes são os quatro métodos básicos de persistência de dados.
 * Isto significa que, a interface PanacheRepository irá fornecer métodos
 * para criar, ler, atualizar e deletar registros da entidade Professor.
 * Ela entrara em ação quando quisermos fazer operações de persistência
 * de dados com a entidade Professor.
 * o repository é realmente o 
 * componente responsável por acessar o banco de dados com métodos específicos.
 */

@ApplicationScoped
public class ProfessorRepository implements PanacheRepository<Professor> {
    // Os métodos básicos são herdados do PanacheRepository
    // listAll(), findById(), persist(), delete(), etc.
}
