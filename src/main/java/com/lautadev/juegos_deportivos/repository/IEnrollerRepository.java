package com.lautadev.juegos_deportivos.repository;

import com.lautadev.juegos_deportivos.model.Enroller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEnrollerRepository extends JpaRepository<Enroller,Long> {
}
