package com.example.school.mapper;

import com.example.school.dto.AlunoDTO;
import com.example.school.dto.MateriaDTO;
import com.example.school.model.Aluno;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class AlunoMapper {

    @Inject
    MateriaMapper materiaMapper;
    
    public AlunoDTO toDTO(Aluno entity) {
        if (entity == null) return null;
        
        // Criamos uma lista de MateriaDTO a partir da relação alunoMaterias
        List<MateriaDTO> materias = new ArrayList<>();
        if (entity.getAlunoMaterias() != null) {
            materias = entity.getAlunoMaterias().stream()
                .map(am -> materiaMapper.toDTO(am.getMateria()))
                .collect(Collectors.toList());
        }
        
        return AlunoDTO.builder()
                .id(entity.getId())
                .nome(entity.getNome() + " " + entity.getSobrenome())
                // Observe que o email não é mapeado porque não existe no modelo Aluno
                // Se quisermos manter o campo email no DTO, podemos definir um valor padrão
                .email(null) // ou algum padrão como "email@dominio.com" ou um campo derivado de nome
                .materias(materias)
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
        // O campo email do DTO é ignorado, pois não existe na entidade Aluno
        
        // Note que não estamos configurando alunoMaterias aqui
        // Isso geralmente é gerenciado pelo método matricularEmMateria ou similar
        
        return aluno;
    }
}