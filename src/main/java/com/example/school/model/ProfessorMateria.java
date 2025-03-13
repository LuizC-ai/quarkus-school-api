package com.example.school.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfessorMateria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "professor_id", nullable = false)
    private Long professorId;

    @Column(name = "materia_id", nullable = false)
    private Long materiaId;

    @ManyToOne
    @JoinColumn(name = "professor_id", insertable = false, updatable = false)
    private Professor professor;

    @ManyToOne
    @JoinColumn(name = "materia_id", insertable = false, updatable = false)
    private Materia materia;
}
