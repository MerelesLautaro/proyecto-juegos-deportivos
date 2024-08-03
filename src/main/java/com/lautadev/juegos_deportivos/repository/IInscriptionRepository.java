package com.lautadev.juegos_deportivos.repository;

import com.lautadev.juegos_deportivos.model.Inscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IInscriptionRepository extends JpaRepository<Inscription,Long> {
}
