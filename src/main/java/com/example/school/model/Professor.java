package com.example.school.model;

import com.example.school.core.ModeloIdBase;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
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
