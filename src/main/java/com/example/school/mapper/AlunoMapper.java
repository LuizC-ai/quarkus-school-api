package com.example.school.mapper;

import com.example.school.dto.AlunoDTO;
import com.example.school.dto.MateriaDTO;
import com.example.school.model.Aluno;
import com.example.school.model.Materia;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.stream.Collectors;

@ApplicationScoped
public class AlunoMapper {

    @Inject
    MateriaMapper materiaMapper;
    
    public AlunoDTO toDTO(Aluno entity) {
        if (entity == null) return null;
        
        return AlunoDTO.builder()
                .id(entity.getId())
                .nome(entity.getNome() + " " + entity.getSobrenome())
                .email(entity.getEmail())
                .materias(entity.getMaterias().stream()
                        .map(materiaMapper::toDTO)
                        .collect(Collectors.toList()))
                .build();
    }
    
    public Aluno toEntity(AlunoDTO dto) {
        if (dto == null) return null;
        
        Aluno aluno = new Aluno();
        // Separar nome e sobrenome se o DTO tiver apenas o nome completo
        if (dto.getNome() != null && dto.getNome().contains(" ")) {
            String[] parts = dto.getNome().split(" ", 2);
            aluno.setNome(parts[0]);
            aluno.setSobrenome(parts[1]);
        } else {
            aluno.setNome(dto.getNome());
        }
        
        aluno.setId(dto.getId());
        aluno.setEmail(dto.getEmail());
        
        return aluno;
    }
}