package com.example.school.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "materias")
public class Materia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private Integer quantidadeHoras;

    @ManyToOne
    @JoinColumn(name = "professor_id")
    private Professor professor;

    @OneToMany(mappedBy = "materia")
    @Builder.Default
    private List<AlunoMateria> materiaAlunos = new ArrayList<>();
    
    @OneToMany(mappedBy = "materia")
    @Builder.Default
    private List<ProfessorMateria> materiaProfessores = new ArrayList<>();

}

