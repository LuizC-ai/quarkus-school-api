package com.example.school.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.school.dto.AlunoDTO;
import com.example.school.dto.MateriaDTO;
import com.example.school.dto.ProfessorDTO;
import com.example.school.model.Aluno;
import com.example.school.model.AlunoMateria;
import com.example.school.model.Materia;
import com.example.school.model.Professor;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "cdi")
public interface EntityMapper {

    // Professor mappings
    ProfessorDTO toProfessorDTO(Professor professor);
    Professor toProfessorEntity(ProfessorDTO dto);
    List<ProfessorDTO> toProfessorDTOList(List<Professor> professors);
    
    // Adicionar método para atualizar entidade existente
    void updateProfessorFromDto(ProfessorDTO dto, @MappingTarget Professor professor);

    // Materia mappings
    MateriaDTO toMateriaDTO(Materia materia);
    Materia toMateriaEntity(MateriaDTO dto);
    List<MateriaDTO> toMateriaDTOList(List<Materia> materias);
    
    // Adicionar método para atualizar entidade existente
    void updateMateriaFromDto(MateriaDTO dto, @MappingTarget Materia materia);

    // Aluno mappings
    @Mapping(target = "materias", ignore = true)
    AlunoDTO toAlunoDTO(Aluno aluno);
    
    @Mapping(target = "materias", ignore = true)
    Aluno toAlunoEntity(AlunoDTO dto);
    
    List<AlunoDTO> toAlunoDTOList(List<Aluno> alunos);
    
    // Adicionar método para atualizar entidade existente
    @Mapping(target = "materias", ignore = true)
    void updateAlunoFromDto(AlunoDTO dto, @MappingTarget Aluno aluno);
    
    default AlunoDTO toAlunoDTOWithMaterias(Aluno aluno, List<AlunoMateria> alunoMaterias) {
        AlunoDTO dto = toAlunoDTO(aluno);
        
        List<MateriaDTO> materias = alunoMaterias.stream()
            .filter(am -> am.getAlunoId().equals(aluno.getId()))
            .map(am -> toMateriaDTO(am.getMateria()))
            .collect(Collectors.toList());
        
        dto.setMaterias(materias);
        return dto;
    }
}
