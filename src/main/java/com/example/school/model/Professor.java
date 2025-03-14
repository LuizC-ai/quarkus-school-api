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
public class Professor extends ModeloIdBase {

    @Getter
    private String nome;

    @Getter
    private String sobrenome;

    @Getter
    private Integer idade;

    public void setNome( String nome ) {
        this.nome = nome;
    }

    public void setSobrenome( String sobrenome ) {
        this.sobrenome = sobrenome;
    }

    public void setIdade( Integer idade ) {
        this.idade = idade;
    }

}
