package com.lautadev.juegos_deportivos.repository;

import com.lautadev.juegos_deportivos.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPermissionRepository extends JpaRepository<Permission,Long> {
}
