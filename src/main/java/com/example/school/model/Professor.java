package com.example.school.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Professor {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nome;
    private String sobrenome;
    private Integer idade;
    
    @OneToMany(mappedBy = "professor", cascade = CascadeType.ALL)
    private List<Materia> materias = new ArrayList<>();

    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getSobrenome() {
        return sobrenome;
    }
    
    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }
    
    public Integer getIdade() {
        return idade;
    }
    
    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public List<Materia> getMaterias() {
        if (materias == null) {
            materias = new ArrayList<>();
        }
        return materias;
    }

    public void setMaterias(List<Materia> materias) {
        this.materias = materias;
    }
}
