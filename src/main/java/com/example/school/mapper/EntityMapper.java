package com.example.school.mapper;

import com.example.school.dto.AlunoDTO;
import com.example.school.dto.MateriaDTO;
import com.example.school.dto.ProfessorDTO;
import com.example.school.model.Aluno;
import com.example.school.model.Materia;
import com.example.school.model.Professor;

import org.mapstruct.*;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "cdi", 
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface EntityMapper {
    
    // Professor mappings
    ProfessorDTO toProfessorDTO(Professor professor);
    
    Professor toProfessorEntity(ProfessorDTO professorDTO);
    
    List<ProfessorDTO> toProfessorDTOList(List<Professor> professors);
    
    void updateProfessorFromDto(ProfessorDTO dto, @MappingTarget Professor entity);
    
    // Materia mappings
    MateriaDTO toMateriaDTO(Materia materia);
    
    Materia toMateriaEntity(MateriaDTO materiaDTO);
    
    List<MateriaDTO> toMateriaDTOList(List<Materia> materias);
    
    void updateMateriaFromDto(MateriaDTO dto, @MappingTarget Materia entity);
    
    // Aluno mappings
    @Mapping(target = "nome", expression = "java(concatenateNomeSobrenome(aluno))")
    @Mapping(target = "materias", source = "materias")
    AlunoDTO toAlunoDTO(Aluno aluno);
    
    @Mapping(target = "nome", expression = "java(extractNome(alunoDTO.getNome()))")
    @Mapping(target = "sobrenome", expression = "java(extractSobrenome(alunoDTO.getNome()))")
    @Mapping(target = "materias", source = "materias")
    Aluno toAlunoEntity(AlunoDTO alunoDTO);
    
    List<AlunoDTO> toAlunoDTOList(List<Aluno> alunos);
    
    @Mapping(target = "nome", expression = "java(extractNome(dto.getNome()))")
    @Mapping(target = "sobrenome", expression = "java(extractSobrenome(dto.getNome()))")
    void updateAlunoFromDto(AlunoDTO dto, @MappingTarget Aluno entity);
    
    // Handle Set to List conversions if needed
    Set<Materia> toMateriaSet(List<MateriaDTO> materiaDTOs);
    List<MateriaDTO> toMateriaDTOList(Set<Materia> materias);
    
    // Helper methods for name handling with improved null safety
    default String concatenateNomeSobrenome(Aluno aluno) {
        if (aluno == null) return "";
        if (aluno.getNome() == null) return aluno.getSobrenome() == null ? "" : aluno.getSobrenome();
        if (aluno.getSobrenome() == null || aluno.getSobrenome().isEmpty()) {
            return aluno.getNome();
        }
        return aluno.getNome() + " " + aluno.getSobrenome();
    }
    
    default String extractNome(String nomeCompleto) {
        if (nomeCompleto == null || nomeCompleto.trim().isEmpty()) return "";
        String[] parts = nomeCompleto.trim().split(" ", 2);
        return parts[0];
    }
    
    default String extractSobrenome(String nomeCompleto) {
        if (nomeCompleto == null || nomeCompleto.trim().isEmpty()) return "";
        String[] parts = nomeCompleto.trim().split(" ", 2);
        return parts.length > 1 ? parts[1] : "";
    }
}