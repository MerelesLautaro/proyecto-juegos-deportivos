package com.lautadev.juegos_deportivos.repository;

import com.lautadev.juegos_deportivos.model.Inscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IInscriptionRepository extends JpaRepository<Inscription,Long> {
    @Query("SELECT i FROM Inscription i JOIN i.participants p WHERE p.dni = :dni")
    List<Inscription> findByParticipantDni(@Param("dni") String dni);
}
