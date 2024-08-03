package com.lautadev.juegos_deportivos.repository;

import com.lautadev.juegos_deportivos.model.Institution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IInstitutionRepository extends JpaRepository<Institution,Long> {
}
