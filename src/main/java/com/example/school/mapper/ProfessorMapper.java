package com.example.school.mapper;

import com.example.school.dto.ProfessorDTO;
import com.example.school.model.Professor;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ProfessorMapper {
    
    @Inject
    EntityMapper entityMapper;
    
    public Professor toEntity(ProfessorDTO dto) {
        if (dto == null) return null;
        
        // Using the MapStruct generated mapper instead of manual mapping
        return entityMapper.toProfessorEntity(dto);
    }
    
    public ProfessorDTO toDTO(Professor entity) {
        if (entity == null) return null;
        
        // Using the MapStruct generated mapper instead of manual mapping
        return entityMapper.toProfessorDTO(entity);
    }
    
    public void updateEntityFromDto(ProfessorDTO dto, Professor entity) {
        if (dto == null) return;
        
        // Using the MapStruct generated mapper for updates
        entityMapper.updateProfessorFromDto(dto, entity);
    }
}
