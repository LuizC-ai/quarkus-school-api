package com.example.school.model;


import jakarta.persistence.Entity;
import com.example.school.core.ModeloIdBase;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "alunos")
public class Aluno extends ModeloIdBase {

    @NotBlank
    private String nome;

    private String sobrenome;

    private Integer idade;

    private String turma;


}
