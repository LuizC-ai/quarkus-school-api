package com.example.school.service;

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
import jakarta.validation.Valid;
import jakarta.ws.rs.NotFoundException;

import java.util.List;
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
    
    public AlunoDTO findById(Long id) {
        Aluno aluno = alunoRepository.findByIdOptional(id)
            .orElseThrow(() -> new NotFoundException("Aluno não encontrado com id: " + id));
        return alunoMapper.toDTO(aluno);
    }
    
    @Transactional
    public AlunoDTO create(AlunoDTO alunoDTO) {
        Aluno aluno = alunoMapper.toEntity(alunoDTO);
        alunoRepository.persist(aluno);
        return alunoMapper.toDTO(aluno);
    }
    
    @Transactional
    public AlunoDTO update(Long id, AlunoDTO alunoDTO) {
        Aluno entity = alunoRepository.findByIdOptional(id)
            .orElseThrow(() -> new NotFoundException("Aluno não encontrado com id: " + id));
        
        alunoMapper.updateEntityFromDTO(alunoDTO, entity);
        
        return alunoMapper.toDTO(entity);
    }
    
    @Transactional
    public void delete(Long id) {
        Aluno aluno = alunoRepository.findByIdOptional(id)
            .orElseThrow(() -> new NotFoundException("Aluno não encontrado com id: " + id));
        alunoRepository.delete(aluno);
    }
    
    /**
     * Recupera todas as matérias em que um aluno está matriculado
     */
    public List<MateriaDTO> getMateriasByAluno(Long id) {
        // Primeiro verificamos se o aluno existe
        alunoRepository.findByIdOptional(id)
            .orElseThrow(() -> new NotFoundException("Aluno não encontrado com id: " + id));
        
        // Buscamos todas as relações aluno-materia para este aluno
        List<AlunoMateria> matriculas = alunoMateriaRepository.findByAlunoId(id);
        
        // Convertemos para uma lista de MateriaDTO
        return matriculas.stream()
            .map(am -> materiaMapper.toDTO(am.getMateria()))
            .collect(Collectors.toList());
    }
    
    /**
     * Recupera todos os professores das matérias em que um aluno está matriculado
     */
    public List<ProfessorDTO> getProfessoresByAluno(Long id) {
        // Verificar se o aluno existe
        alunoRepository.findByIdOptional(id)
            .orElseThrow(() -> new NotFoundException("Aluno não encontrado com id: " + id));
        
        // Buscar todas as matérias do aluno
        List<AlunoMateria> matriculas = alunoMateriaRepository.findByAlunoId(id);
        
        // Coletar professores únicos
        return matriculas.stream()
            .map(am -> am.getMateria())
            .filter(materia -> materia.getProfessor() != null)
            .map(materia -> professorMapper.toDTO(materia.getProfessor()))
            .distinct()
            .collect(Collectors.toList());
    }
    
    /**
     * Matricula um aluno em uma matéria
     */
    @Transactional
    public AlunoDTO matricularEmMateria(Long alunoId, Long materiaId) {
        // Buscar o aluno e a matéria, lançando exceção se não encontrados
        Aluno aluno = alunoRepository.findByIdOptional(alunoId)
            .orElseThrow(() -> new NotFoundException("Aluno não encontrado com id: " + alunoId));
        
        Materia materia = materiaRepository.findByIdOptional(materiaId)
            .orElseThrow(() -> new NotFoundException("Matéria não encontrada com id: " + materiaId));
        
        // Verificar se o aluno já está matriculado nesta matéria
        boolean jaMatriculado = alunoMateriaRepository.find("aluno.id = ?1 AND materia.id = ?2", 
                                                          alunoId, materiaId).count() > 0;
        
        if (!jaMatriculado) {
            // Criar a associação entre aluno e matéria
            AlunoMateria alunoMateria = new AlunoMateria();
            alunoMateria.setAluno(aluno);
            alunoMateria.setMateria(materia);
            alunoMateriaRepository.persist(alunoMateria);
        }
        
        return alunoMapper.toDTO(aluno);
    }
}