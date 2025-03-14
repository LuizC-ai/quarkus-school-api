package com.example.school.dto;

import com.example.school.model.Professor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MateriaDTO {
    private Long id;
    private String nome;
    private Professor professor;
    
    // Adicionando o campo faltante
    private Integer quantidadeHoras;
    
    // Outros campos...
}
