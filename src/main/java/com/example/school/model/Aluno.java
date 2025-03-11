package com.example.school.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Table(name = "alunos")
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String sobrenome;
    private Integer idade;
    private String turma;

    @OneToMany(mappedBy = "aluno")
    @Builder.Default
    private List<AlunoMateria> alunoMaterias = new ArrayList<>();
    
    public void matricularEm(Materia materia) {
        AlunoMateria alunoMateria = AlunoMateria.builder()
            .alunoId(this.getId())
            .materiaId(materia.getId())
            .aluno(this)
            .materia(materia)
            .build();
            
        this.alunoMaterias.add(alunoMateria);
        materia.getMateriaAlunos().add(alunoMateria);
    }
}
