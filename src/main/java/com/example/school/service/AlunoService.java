package com.example.school.service;

import com.example.school.exception.ResourceNotFoundException;
import com.example.school.model.*;
import com.example.school.dto.AlunoDTO;
import com.example.school.dto.MateriaDTO;
import com.example.school.dto.ProfessorDTO;
import com.example.school.repository.AlunoRepository;
import com.example.school.repository.MateriaRepository;
import com.example.school.repository.AlunoMateriaRepository;
import com.example.school.mapper.AlunoMapper;
import com.example.school.mapper.MateriaMapper;
import com.example.school.mapper.ProfessorMapper;

import com.example.school.repository.ProfessorMateriaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class AlunoService {

    @Inject
    AlunoRepository alunoRepository;
    
    @Inject
    MateriaRepository materiaRepository;
    
    @Inject
    AlunoMateriaRepository alunoMateriaRepository;
    
    @Inject
    AlunoMapper alunoMapper;
    
    @Inject
    MateriaMapper materiaMapper;
    
    @Inject
    ProfessorMapper professorMapper;

    @Inject
    MatriculaService matriculaService;

    @Inject
    ProfessorMateriaRepository professorMateriaRepository;

    public List<AlunoDTO> findAll() {
        return alunoMapper.toDTOList(alunoRepository.listAll());
    }
    
    public AlunoDTO findByIdentificador(String identificador) {
        Aluno aluno = alunoRepository.findByIdentificador(identificador)
            .orElseThrow(() -> new ResourceNotFoundException("Aluno não encontrado com identificador: " + identificador));
        if ( aluno == null ) {
            throw new ResourceNotFoundException( "Aluno não encontrado com id: " + identificador );
        }
        return alunoMapper.toDTO( aluno );
    }

    @Transactional
    public AlunoDTO create(AlunoDTO alunoDTO) {
        Aluno aluno = alunoMapper.toEntity(alunoDTO);
        alunoRepository.persist(aluno);
        return alunoMapper.toDTO(aluno);
    }
    
    @Transactional
    public AlunoDTO update(String identificador, AlunoDTO alunoDTO) {
        Aluno entity = alunoRepository.findByIdentificador(identificador).orElseThrow(() -> new ResourceNotFoundException("Aluno não encontrado com identificador: " + identificador));
        alunoMapper.updateEntityFromDTO(alunoDTO, entity);
        return alunoMapper.toDTO(entity);
    }

    @Transactional
    public void delete(String identificador) {
        matriculaService.delete( identificador );
    }

    private Aluno findByEntityByIdentificador (String identificador){
        return alunoRepository.findByIdentificador(identificador)
                .orElseThrow(() -> new ResourceNotFoundException("Aluno não encontrado com identificador: " + identificador));
    }

    public List<MateriaDTO> getMateriasByAlunoIdentificador(String identificador) {
        verificarAlunoExiste( identificador );

        List<AlunoMateria> matriculas = alunoMateriaRepository.find("aluno.identificador", identificador).list();

        return matriculas.stream()
            .map(am -> materiaMapper.toDTO(am.getMateria()))
            .collect(Collectors.toList());
    }

    private void verificarAlunoExiste( String identificador ) {
        if ( !alunoExiste( identificador ) ) {
            throw new ResourceNotFoundException( "Aluno não encontrado com identificador " + identificador );
        }
    }

    private boolean alunoExiste( String identificador ) {
        return alunoRepository.existsByIdentificador( identificador );
    }

    public List<ProfessorDTO> getProfessoresByAlunoIdentificador(String identificador) {
        Aluno aluno = alunoRepository.findByIdentificador( identificador )
                .orElseThrow( ( ) -> new ResourceNotFoundException( "Aluno não encontrado com identificador: " + identificador ) );

        List<AlunoMateria> matriculas = alunoMateriaRepository.findByAlunoIdentificador( identificador );
        Set<Professor> professorSet = new HashSet<>();

        for (AlunoMateria matricula : matriculas) {
            String materiaId = matricula.getMateria().getIdentificador();
            List<ProfessorMateria> professorMaterias = professorMateriaRepository
                    .findByMateriaIdentificador( materiaId );

            for (ProfessorMateria pm : professorMaterias ) {
                professorSet.add(pm.getProfessor());
            }
        }

        return professorSet.stream()
                .map(professor -> professorMapper.toDTO(professor))
                .collect( Collectors.toList() );
    }

    @Transactional
    public AlunoDTO matricularEmMateria(String alunoIdentificador, String materiaIdentificador){
        matriculaService.matricular( alunoIdentificador, materiaIdentificador );

        Aluno aluno = alunoRepository.findByIdentificador( alunoIdentificador )
            .orElseThrow(() -> new ResourceNotFoundException("Aluno não encontrado com identificador: " + alunoIdentificador));
        return alunoMapper.toDTO(aluno);
    }

    // No AlunoService
    public List<MateriaDTO> getMateriasByAluno(String identificador) {
        return getMateriasByAlunoIdentificador(identificador);
    }

    public List<ProfessorDTO> getProfessoresByAluno(String identificador) {
        return getProfessoresByAlunoIdentificador(identificador);
    }
}