package org.acme.model;

import javax.persistence.*;
import java.util.Set;
import java.util.HashSet;

@Entity
public class Professor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    // Other fields
    
    // Replace @OneToMany with @ManyToMany or use join table explicitly
    // PostgreSQL prefers using join tables for collections rather than direct list mappings
    @ManyToMany
    @JoinTable(
        name = "professor_materia", 
        joinColumns = @JoinColumn(name = "professor_id"),
        inverseJoinColumns = @JoinColumn(name = "materia_id")
    )
    private Set<Materia> materias = new HashSet<>();
    
    // Getters and setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Set<Materia> getMaterias() {
        return materias;
    }
    
    public void setMaterias(Set<Materia> materias) {
        this.materias = materias;
    }
}
