package com.lautadev.juegos_deportivos.service;

import com.lautadev.juegos_deportivos.model.Category;

import java.util.List;
import java.util.Optional;

public interface ICategoryService {
    public void saveCategory(Category category);
    public List<Category> getCategories();
    public Optional<Category> findCategory(Long id);
    public void deleteCategory(Long id);
    public Category editCategory(Long id,Category category);
}
