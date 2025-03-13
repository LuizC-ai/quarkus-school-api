package com.example.school.mapper;

import com.example.school.dto.AlunoDTO;
import com.example.school.dto.MateriaDTO;
import com.example.school.dto.ProfessorDTO;
import com.example.school.model.Aluno;
import com.example.school.model.Materia;
import com.example.school.model.Professor;

import org.mapstruct.*;

import java.util.List;

@Mapper(
    componentModel = "cdi", 
    uses = {},
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    unmappedSourcePolicy = ReportingPolicy.IGNORE  // Adicione esta linha
)
public interface EntityMapper {
    
    // Professor mappings
    ProfessorDTO toProfessorDTO(Professor professor);
    Professor toProfessorEntity(ProfessorDTO professorDTO);
    List<ProfessorDTO> toProfessorDTOList(List<Professor> professors);
    void updateProfessorFromDto(ProfessorDTO dto, @MappingTarget Professor entity);
    
    // Materia mappings
    @BeanMapping(ignoreUnmappedSourceProperties = {"materiaAlunos", "materiaProfessores"})
    MateriaDTO toMateriaDTO(Materia materia);
    
    Materia toMateriaEntity(MateriaDTO materiaDTO);
    
    List<MateriaDTO> toMateriaDTOList(List<Materia> materias);
    
    @Mapping(target = "materiaAlunos", ignore = true)
    @Mapping(target = "materiaProfessores", ignore = true)
    void updateMateriaFromDto(MateriaDTO dto, @MappingTarget Materia entity);
    
    // Aluno mappings
    @Mapping(target = "nome", expression = "java(aluno.getNome() + \" \" + aluno.getSobrenome())")
    @Mapping(target = "email", constant = "")
    @Mapping(source = "alunoMaterias", target = "materias")
    AlunoDTO toAlunoDTO(Aluno aluno);
    
    @Mapping(target = "nome", expression = "java(splitNome(alunoDTO.getNome())[0])")
    @Mapping(target = "sobrenome", expression = "java(splitNome(alunoDTO.getNome())[1])")
    @Mapping(target = "alunoMaterias", ignore = true)
    Aluno toAlunoEntity(AlunoDTO alunoDTO);
    
    @Mapping(target = "nome", expression = "java(splitNome(dto.getNome())[0])")
    @Mapping(target = "sobrenome", expression = "java(splitNome(dto.getNome())[1])")
    @Mapping(target = "alunoMaterias", ignore = true)
    void updateAlunoFromDto(AlunoDTO dto, @MappingTarget Aluno entity);
    
    // Método auxiliar para separar nome e sobrenome
    default String[] splitNome(String nomeCompleto) {
        if (nomeCompleto == null) return new String[] {"", ""};
        String[] parts = nomeCompleto.split(" ", 2);
        return parts.length > 1 ? parts : new String[] {parts[0], ""};
    }
}