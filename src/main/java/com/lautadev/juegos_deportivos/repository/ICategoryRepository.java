package com.lautadev.juegos_deportivos.repository;

import com.lautadev.juegos_deportivos.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoryRepository extends JpaRepository<Category,Long> {
}
