package com.example.school.mapper;

import com.example.school.model.Aluno;
import com.example.school.dto.AlunoDTO;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class AlunoMapper {

    public AlunoDTO toDTO(Aluno entity) {
        if (entity == null) return null;
        
        AlunoDTO dto = new AlunoDTO();
        dto.setIdentificador(entity.getIdentificador());
        dto.setNome(entity.getNome());
        dto.setSobrenome(entity.getSobrenome());
        dto.setIdade(entity.getIdade());
        dto.setTurma(entity.getTurma());
        
        return dto;
    }
    
    public Aluno toEntity(AlunoDTO dto) {
        if (dto == null) return null;
        
        Aluno entity = new Aluno();
        
        entity.setNome(dto.getNome());
        entity.setSobrenome(dto.getSobrenome());
        entity.setIdade(dto.getIdade());
        entity.setTurma(dto.getTurma());
        
        return entity;
    }
    
    public void updateEntityFromDTO(AlunoDTO dto, Aluno entity) {
        if (dto.getNome() != null) entity.setNome(dto.getNome());
        if (dto.getSobrenome() != null) entity.setSobrenome(dto.getSobrenome());
        if (dto.getIdade() != null) entity.setIdade(dto.getIdade());
        if (dto.getTurma() != null) entity.setTurma(dto.getTurma());
    }
    
    public List<AlunoDTO> toDTOList(List<Aluno> entities) {
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}