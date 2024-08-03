package com.lautadev.juegos_deportivos.repository;

import com.lautadev.juegos_deportivos.model.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IParticipantRepository extends JpaRepository<Participant,Long> {
}
