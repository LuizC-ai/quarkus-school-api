package com.example.school.mapper;

import com.example.school.dto.ProfessorDTO;
import com.example.school.model.Professor;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProfessorMapper {
    
    public Professor toEntity(ProfessorDTO dto) {
        if (dto == null) return null;
        
        Professor entity = new Professor();
        entity.setNome(dto.getNome());
        entity.setSobrenome(dto.getSobrenome());
        entity.setIdade(dto.getIdade());
        return entity;
    }
    
    public ProfessorDTO toDTO(Professor entity) {
        if (entity == null) return null;
        
        ProfessorDTO dto = new ProfessorDTO();
        dto.setNome(entity.getNome());
        dto.setSobrenome(entity.getSobrenome());
        dto.setIdade(entity.getIdade());
        return dto;
    }
}
