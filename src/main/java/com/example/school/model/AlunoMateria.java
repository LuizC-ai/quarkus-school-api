package com.example.school.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlunoMateria extends ModeloIdBase {

    @NotNull
    @ManyToOne
    private Aluno aluno;

    @NotNull
    @ManyToOne
    private Materia materia;
    
    @Override
    protected String definirPrefixo() {
        return "AM"; // AlunoMateria
    }
}
