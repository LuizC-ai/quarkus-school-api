package com.example.school.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Professor {

    @Getter
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    @Getter
    private String nome;

    @Getter
    private String sobrenome;

    @Getter
    private Integer idade;

    @OneToMany( mappedBy = "professor", cascade = CascadeType.ALL )
    private List<Materia> materias = new ArrayList<>( );

    public void setId( Long id ) {
        this.id = id;
    }

    public void setNome( String nome ) {
        this.nome = nome;
    }

    public void setSobrenome( String sobrenome ) {
        this.sobrenome = sobrenome;
    }

    public void setIdade( Integer idade ) {
        this.idade = idade;
    }

    public void setMaterias( List<Materia> materias ) {
        this.materias = materias;
    }
}
