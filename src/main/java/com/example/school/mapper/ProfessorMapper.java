package com.example.school.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.example.school.dto.ProfessorDTO;
import com.example.school.model.Professor;

import jakarta.enterprise.context.ApplicationScoped;
@ApplicationScoped
public class ProfessorMapper {

    public ProfessorDTO toDTO(Professor entity) {
        if (entity == null) return null;
        
        ProfessorDTO dto = new ProfessorDTO();
        dto.setId(entity.getId());
        dto.setNome(entity.getNome());
        dto.setSobrenome(entity.getSobrenome());
        dto.setIdade(entity.getIdade());
        
        
        return dto;
    }
    
    public Professor toEntity(ProfessorDTO dto) {
        if (dto == null) return null;
        
        Professor entity = new Professor();
        
        // Se for uma atualização, não configuramos o ID
        if (dto.getId() != null) {
            entity.setId(dto.getId());
        }
        
        entity.setNome(dto.getNome());
        entity.setSobrenome(dto.getSobrenome());
        entity.setIdade(dto.getIdade());
        
        return entity;
    }
    
    public void updateEntityFromDTO(ProfessorDTO dto, Professor entity) {
        if (dto.getNome() != null) entity.setNome(dto.getNome());
        if (dto.getSobrenome() != null) entity.setSobrenome(dto.getSobrenome());
        if (dto.getIdade() != null) entity.setIdade(dto.getIdade());
    }
        
    
    public List<ProfessorDTO> toDTOList(List<Professor> entities) {
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}

