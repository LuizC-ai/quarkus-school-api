package com.example.school.dto;

import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AlunoDTO extends BaseDTO {

    private String nome;

    private String email;

    private String sobrenome;

    private Integer idade;

    private String turma;

    private List<MateriaDTO> materias;
}

