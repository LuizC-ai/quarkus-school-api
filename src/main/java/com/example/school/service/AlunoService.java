package com.example.school.service;

import com.example.school.model.Aluno;
import com.example.school.model.Materia;
import com.example.school.model.AlunoMateria;
import com.example.school.dto.MateriaDTO;
import com.example.school.dto.ProfessorDTO;
import com.example.school.repository.AlunoRepository;
import com.example.school.repository.MateriaRepository;
import com.example.school.repository.AlunoMateriaRepository;
import com.example.school.mapper.MateriaMapper;
import com.example.school.mapper.ProfessorMapper;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
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
    MateriaMapper materiaMapper;
    
    @Inject
    ProfessorMapper professorMapper;
    
    public List<Aluno> listAll() {
        return alunoRepository.listAll();
    }
    
    public Aluno findById(Long id) {
        return alunoRepository.findByIdOptional(id)
            .orElseThrow(() -> new NotFoundException("Aluno não encontrado com id: " + id));
    }
    
    @Transactional
    public Aluno create(Aluno aluno) {
        alunoRepository.persist(aluno);
        return aluno;
    }
    
    @Transactional
    public Aluno update(Long id, Aluno aluno) {
        Aluno entity = findById(id);
        
        if (aluno.getNome() != null) entity.setNome(aluno.getNome());
        if (aluno.getSobrenome() != null) entity.setSobrenome(aluno.getSobrenome());
        if (aluno.getIdade() != null) entity.setIdade(aluno.getIdade());
        if (aluno.getTurma() != null) entity.setTurma(aluno.getTurma());
        
        return entity;
    }
    
    @Transactional
    public void delete(Long id) {
        Aluno aluno = findById(id);
        alunoRepository.delete(aluno);
    }

    public List<Aluno> findAll() {
        return alunoRepository.listAll();
    }
    
    /**
     * Recupera todas as matérias em que um aluno está matriculado
     * 
     * @param id ID do aluno
     * @return Lista de DTOs de matérias
     * @throws NotFoundException se o aluno não for encontrado
     */
    public List<MateriaDTO> getMateriasByAluno(Long id) {
        // Primeiro verificamos se o aluno existe
        Aluno aluno = findById(id);
        
        // Buscamos todas as relações aluno-materia para este aluno
        List<AlunoMateria> matriculas = alunoMateriaRepository.findByAlunoId(id);
        
        // Convertemos para uma lista de MateriaDTO
        return matriculas.stream()
            .map(am -> materiaMapper.toDTO(am.getMateria()))
            .collect(Collectors.toList());
    }
    
    /**
     * Recupera todos os professores das matérias em que um aluno está matriculado
     *
     * @param id ID do aluno
     * @return Lista de DTOs de professores
     * @throws NotFoundException se o aluno não for encontrado
     */
    public List<ProfessorDTO> getProfessoresByAluno(Long id) {
        // Verificar se o aluno existe
        Aluno aluno = findById(id);
        
        // Buscar todas as matérias do aluno
        List<AlunoMateria> matriculas = alunoMateriaRepository.findByAlunoId(id);
        
        // Coletar professores únicos (uma matéria pode ter apenas um professor)
        return matriculas.stream()
            .map(am -> am.getMateria())
            .filter(materia -> materia.getProfessor() != null) // Ignora matérias sem professor
            .map(materia -> professorMapper.toDTO(materia.getProfessor()))
            .distinct() // Remove duplicatas (caso o aluno tenha mais de uma matéria com mesmo professor)
            .collect(Collectors.toList());
    }
    
    /**
     * Matricula um aluno em uma matéria
     * 
     * @param alunoId ID do aluno
     * @param materiaId ID da matéria
     * @return O aluno após matriculado
     * @throws NotFoundException se o aluno ou a matéria não forem encontrados
     */
    @Transactional
    public Aluno matricularEmMateria(Long alunoId, Long materiaId) {
        // Buscar o aluno e a matéria, lançando exceção se não encontrados
        Aluno aluno = findById(alunoId);
        
        Materia materia = materiaRepository.findByIdOptional(materiaId)
            .orElseThrow(() -> new NotFoundException("Matéria não encontrada com id: " + materiaId));
        
        // Criar a associação entre aluno e matéria usando o método já existente no modelo
        aluno.matricularEm(materia);
        
        // Persistir a associação (o método matricularEm já criou o objeto AlunoMateria, 
        // mas ainda precisamos persistir)
        // Como estamos dentro de uma transação, as alterações serão commitadas ao final
        
        return aluno;
    }
}