package com.lautadev.juegos_deportivos.service;

import com.lautadev.juegos_deportivos.model.Discipline;

import java.util.List;
import java.util.Optional;

public interface IDisciplineService {
    public void saveDiscipline(Discipline discipline);
    public List<Discipline> getDisciplines();
    public Optional<Discipline> findDiscipline(Long id);
    public void deleteDiscipline(Long id);
    public Discipline editDiscipline(Long id,Discipline discipline);
}
