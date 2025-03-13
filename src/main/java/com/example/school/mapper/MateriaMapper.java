package com.example.school.mapper;

import com.example.school.dto.MateriaDTO;
import com.example.school.model.Materia;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MateriaMapper {
    
    public MateriaDTO toDTO(Materia entity) {
        if (entity == null) return null;
        
        return MateriaDTO.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .build();
    }
    
    public Materia toEntity(MateriaDTO dto) {
        if (dto == null) return null;
        
        Materia materia = new Materia();
        materia.setId(dto.getId());
        materia.setNome(dto.getNome());
        
        return materia;
    }
}