package com.lautadev.juegos_deportivos.repository;

import com.lautadev.juegos_deportivos.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepository extends JpaRepository<Role,Long> {
}
