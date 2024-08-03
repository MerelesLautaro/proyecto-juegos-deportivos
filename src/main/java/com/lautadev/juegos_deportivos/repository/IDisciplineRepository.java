package com.lautadev.juegos_deportivos.repository;

import com.lautadev.juegos_deportivos.model.Discipline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDisciplineRepository extends JpaRepository<Discipline,Long> {
}
