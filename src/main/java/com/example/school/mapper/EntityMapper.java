package com.example.school.mapper;


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
    ProfessorDTO toProfessorDTO( Professor professor);
    Professor toProfessorEntity(ProfessorDTO dto);
    List<ProfessorDTO> toProfessorDTOList(List<Professor> professors);

    // Materia mappings
    MateriaDTO toMateriaDTO( Materia materia);
    Materia toMateriaEntity(MateriaDTO dto);
    List<MateriaDTO> toMateriaDTOList(List<Materia> materias);

    // Aluno mappings
    @Mapping(target = "materias", ignore = true)
    AlunoDTO toAlunoDTO( Aluno aluno);
    
    @Mapping(target = "materias", ignore = true)
    Aluno toAlunoEntity( AlunoDTO dto);
    
    List<AlunoDTO> toAlunoDTOList(List<Aluno> alunos);
    
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
