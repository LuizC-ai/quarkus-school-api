package com.example.school.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Table(name = "professores")
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String sobrenome;
    private Integer idade;

    /*
     * Está associação estará mapeada na classe Materia, 
     * onde esta o atributo professor.
     * 
     * O builder.Default é uma anotação do lombok que cria uma lista vazia
     * para evitar que a lista seja nula. Isto ajuda a evitar NullPointerException.
     */
    @OneToMany(mappedBy = "professor", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Materia> materias = new ArrayList<>();
}
