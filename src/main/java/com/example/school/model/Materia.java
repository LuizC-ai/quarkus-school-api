package com.example.school.model;


import jakarta.persistence.Entity;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import com.example.school.core.ModeloIdBase;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "materias")
public class Materia extends ModeloIdBase {

    private String nome;
    private Integer quantidadeHoras;

    @ManyToOne
    @JoinColumn(name = "professor_id")
    private Professor professor;

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

}

