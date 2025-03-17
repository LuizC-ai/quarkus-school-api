package com.example.school.dto;

import com.example.school.model.Professor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MateriaDTO extends BaseDTO {

    private String nome;
    private Professor professor;
    

    private Integer quantidadeHoras;
    

}
