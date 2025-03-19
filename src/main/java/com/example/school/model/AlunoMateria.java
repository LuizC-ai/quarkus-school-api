package com.example.school.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.example.school.core.ModeloIdBase;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlunoMateria extends ModeloIdBase {

    @NotNull
    @ManyToOne
    private Aluno aluno;

    @NotNull
    @ManyToOne
    private Materia materia;

}
