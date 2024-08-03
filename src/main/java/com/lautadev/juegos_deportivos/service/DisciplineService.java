package com.lautadev.juegos_deportivos.service;

import com.lautadev.juegos_deportivos.model.Discipline;
import com.lautadev.juegos_deportivos.repository.IDisciplineRepository;
import com.lautadev.juegos_deportivos.util.NullAwareBeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DisciplineService implements IDisciplineService{
    @Autowired
    private IDisciplineRepository disciplineRepository;

    @Override
    public void saveDiscipline(Discipline discipline) {
        disciplineRepository.save(discipline);
    }

    @Override
    public List<Discipline> getDisciplines() {
        return disciplineRepository.findAll();
    }

    @Override
    public Optional<Discipline> findDiscipline(Long id) {
        return disciplineRepository.findById(id);
    }

    @Override
    public void deleteDiscipline(Long id) {
        disciplineRepository.deleteById(id);
    }

    @Override
    public void editDiscipline(Long id,Discipline discipline) {
        Discipline disciplineEdit = this.findDiscipline(id).orElse(null);

        // Copy non-null properties
        NullAwareBeanUtils.copyNonNullProperties(discipline, disciplineEdit);

        assert disciplineEdit != null;
        disciplineRepository.save(disciplineEdit);
    }
}
