package com.example.school.service;

import com.example.school.exception.ResourceNotFoundException;
import com.example.school.model.Aluno;
import com.example.school.model.Materia;
import com.example.school.model.AlunoMateria;
import com.example.school.dto.AlunoDTO;
import com.example.school.dto.MateriaDTO;
import com.example.school.dto.ProfessorDTO;
import com.example.school.repository.AlunoRepository;
import com.example.school.repository.MateriaRepository;
import com.example.school.repository.AlunoMateriaRepository;
import com.example.school.mapper.AlunoMapper;
import com.example.school.mapper.MateriaMapper;
import com.example.school.mapper.ProfessorMapper;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.util.List;
import java.util.Optional;
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
    
    public List<AlunoDTO> findAll() {
        return alunoMapper.toDTOList(alunoRepository.listAll());
    }
    
    public AlunoDTO findByIdentificador(String identificador) {
        Aluno aluno = alunoRepository.find( "identificador", identificador ).firstResult( );
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
        Aluno entity = alunoRepository.find( "identificador", identificador ).firstResult();
        if (entity == null) {
            throw new NotFoundException("Aluno não encontrado com id: " + identificador);
        }
        alunoMapper.updateEntityFromDTO(alunoDTO, entity);
        return alunoMapper.toDTO(entity);
    }
    
    @Transactional
    public void delete(String identificador) {
        Aluno aluno = alunoRepository.find( "identificador", identificador ).firstResult();
        if (aluno == null) {
            throw new NotFoundException("Aluno não encontrado com id: " + identificador);
        }
        alunoRepository.delete(aluno);
    }
    

    public List<MateriaDTO> getMateriasByAlunoIdentificador(String identificador) {
        Optional<Aluno> aluno = alunoRepository.find( "identificador", identificador ).singleResultOptional();
        if (aluno.isEmpty()) {
            throw new NotFoundException("Aluno não encontrado com id: " + identificador);
        }

        List<AlunoMateria> matriculas = alunoMateriaRepository.find("aluno.identificador", identificador).list();

        return matriculas.stream()
            .map(am -> materiaMapper.toDTO(am.getMateria()))
            .collect(Collectors.toList());
    }

    public List<ProfessorDTO> getProfessoresByAlunoIdentificador(String identificador) {
        Aluno aluno = alunoRepository.find( "identificador", identificador ).firstResult();
        if (aluno == null) {
            throw new ResourceNotFoundException("Aluno não encontrado com identificador: " + identificador);
        }

        List<AlunoMateria> matriculas = alunoMateriaRepository.find("aluno.identificador", identificador).list();

        return matriculas.stream()
            .map(am -> am.getMateria())
            .filter(materia -> materia.getProfessor() != null)
            .map(materia -> professorMapper.toDTO(materia.getProfessor()))
            .distinct()
            .collect(Collectors.toList());
    }

    @Transactional
    public AlunoDTO matricularEmMateria(String alunoIdentificador, String materiaIdentificador){
        Aluno aluno = alunoRepository.find( "identificador", alunoIdentificador ).firstResult();
        if ( aluno == null ) {
            throw new ResourceNotFoundException("Aluno não encontrado com identificador: " + alunoIdentificador);
        }

        Materia materia = materiaRepository.find("identificador", materiaIdentificador).firstResult();
        if (materia == null) {
            throw new ResourceNotFoundException("Matéria não encontrada com identificador: " + materiaIdentificador);
        }

        boolean jaMatriculado = alunoMateriaRepository.find(
                "aluno.identificador = ?1 AND materia.identificador = ?2",
                alunoIdentificador, materiaIdentificador).count() > 0;

        if (!jaMatriculado) {
            AlunoMateria alunoMateria = new AlunoMateria();
            alunoMateria.setAluno(aluno);
            alunoMateria.setMateria(materia);
            alunoMateriaRepository.persist(alunoMateria);
        }

        return alunoMapper.toDTO(aluno);
    }
}