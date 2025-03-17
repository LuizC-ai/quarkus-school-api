package com.example.school.model;

import com.example.school.core.ModeloIdBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfessorMateria extends ModeloIdBase {

    @NotNull
    @ManyToOne
    private Professor professor;

    @NotNull
    @ManyToOne
    private Materia materia;

}
