package com.example.school.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.example.school.dto.MateriaDTO;
import com.example.school.model.Materia;

import jakarta.enterprise.context.ApplicationScoped;
@ApplicationScoped
public class MateriaMapper {

    public MateriaDTO toDTO(Materia entity) {
        if (entity == null) return null;
        
        MateriaDTO dto = new MateriaDTO();
        dto.setNome(entity.getNome());
        dto.setProfessor(entity.getProfessor());
        dto.setQuantidadeHoras(entity.getQuantidadeHoras());
        
        
        return dto;
    }
    
    public Materia toEntity(MateriaDTO dto) {
        if (dto == null) return null;
        
        Materia entity = new Materia();
        
        // Se for uma atualização, não configuramos o ID
        if (dto.getId() != null) {
            entity.setId(dto.getId());
        }
        
        entity.setNome(dto.getNome());
        entity.setProfessor(dto.getProfessor());
        entity.setQuantidadeHoras(dto.getQuantidadeHoras());
        return entity;
    }
    
    public void updateEntityFromDTO(MateriaDTO dto, Materia entity) {
        if (dto.getNome() != null) entity.setNome(dto.getNome());
        if (dto.getProfessor() != null) entity.setProfessor(dto.getProfessor());
        if (dto.getQuantidadeHoras() != null) entity.setQuantidadeHoras(dto.getQuantidadeHoras());
    }
        
    
    public List<MateriaDTO> toDTOList(List<Materia> entities) {
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}

