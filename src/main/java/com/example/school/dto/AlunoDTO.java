package com.example.school.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlunoDTO {
    private Long id;
    private String nome;
    private String email;
    private String sobrenome;
    private Integer idade;
    private String turma;
    private List<MateriaDTO> materias;
}

