package com.example.school.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlunoMateria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "aluno_id", nullable = false)
    private Long alunoId;

    @Column(name = "materia_id", nullable = false)
    private Long materiaId;

    @ManyToOne
    @JoinColumn(name = "aluno_id", insertable = false, updatable = false)
    private Aluno aluno;

    @ManyToOne
    @JoinColumn(name = "materia_id", insertable = false, updatable = false)
    private Materia materia;
}
