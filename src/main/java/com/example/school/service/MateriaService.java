package com.example.school.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.example.school.dto.MateriaDTO;
import com.example.school.exception.ResourceNotFoundException;
import com.example.school.mapper.MateriaMapper;
import com.example.school.model.AlunoMateria;
import com.example.school.model.Materia;
import com.example.school.model.Professor;
import com.example.school.model.ProfessorMateria;
import com.example.school.repository.AlunoMateriaRepository;
import com.example.school.repository.MateriaRepository;
import com.example.school.repository.ProfessorMateriaRepository;
import com.example.school.repository.ProfessorRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;

@ApplicationScoped
public class MateriaService {

    @Inject
    MateriaRepository materiaRepository;
    
    @Inject
    ProfessorRepository professorRepository;
    
    @Inject
    MateriaMapper mapper;

    @Inject
    ProfessorMateriaRepository professorMateriaRepository;

    @Inject
    AlunoMateriaRepository alunoMateriaRepository;
    
    public List<MateriaDTO> findAll() {
        List<Materia> materias = materiaRepository.listAll();
        return mapper.toDTOList(materias);
    }

    public MateriaDTO findByIdentificador(String identificador) {
        Materia materia = materiaRepository.findByIdentificador(identificador)
                .orElseThrow(() -> new ResourceNotFoundException("Materia não encontrada com identificador: " + identificador));
        return mapper.toDTO(materia);
    }

    @Transactional
    public MateriaDTO create(MateriaDTO materiaDTO) {
        Materia materia = mapper.toEntity(materiaDTO);
        materiaRepository.persist(materia);
        return mapper.toDTO(materia);
    }

    @Transactional
    public MateriaDTO update(String identificador, MateriaDTO materiaDTO) {
        Objects.requireNonNull(materiaDTO, "Materia não pode ser nula");

        Materia entity = findEntityByIdentificador(identificador);
        mapper.updateEntityFromDTO(materiaDTO, entity);

        return mapper.toDTO(entity);
    }

    private Materia findEntityByIdentificador( String identificador ) {
        Optional<Materia> materia = materiaRepository.findByIdentificador( identificador );
        if ( materia.isEmpty( ) ) {
            throw new ResourceNotFoundException("Materia não encontrada com identificador: " + identificador);
        }
        return materia.get( );
    }

    @Transactional
    public void delete(String identificador) {
        Materia materia = materiaRepository.findByIdentificador(identificador)
            .orElseThrow(() -> new ResourceNotFoundException("Materia não encontrada com identificador: " + identificador));
        List<AlunoMateria> alunoMaterias = alunoMateriaRepository.findByMateriaIdentificador(identificador);
        for (AlunoMateria relacao : alunoMaterias) {
            alunoMateriaRepository.delete(relacao);
        }

        List<ProfessorMateria> professorMaterias = professorMateriaRepository.findByMateriaIdentificador(identificador);
        for (ProfessorMateria relacao : professorMaterias) {
            professorMateriaRepository.delete(relacao);
        }

        materiaRepository.delete(materia);
    }

    @Transactional
    public MateriaDTO associarProfessor( @NotBlank String materiaIdentificador, @NotBlank String professorIdentificador) {

        Materia materia = materiaRepository.findByIdentificador(materiaIdentificador)
                .orElseThrow(() -> new ResourceNotFoundException("Materia não encontrada com identificador: " + materiaIdentificador));

        Professor professor = professorRepository.findByIdentificador(professorIdentificador)
                .orElseThrow(() -> new ResourceNotFoundException("Professor não encontrado com identificador: " + professorIdentificador));

        boolean associacaoJaExiste = professorMateriaRepository
                .existsByProfessorAndMateriaIdentificador(professorIdentificador, materiaIdentificador);

        if (associacaoJaExiste) {
            throw new IllegalArgumentException( "Professor já associado a esta matéria" );
        }
        ProfessorMateria professorMateria = new ProfessorMateria();
        professorMateria.setProfessor(professor);
        professorMateria.setMateria(materia);
        professorMateriaRepository.persist(professorMateria);
        return mapper.toDTO(materia);
    }

    private Materia findEntityById(Long id) {
        return materiaRepository.findByIdOptional(id)
            .orElseThrow(() -> new ResourceNotFoundException("Materia não encontrada com identificador: " + id));
    }
}
