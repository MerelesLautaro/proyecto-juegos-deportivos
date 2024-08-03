package com.lautadev.juegos_deportivos.service;

import com.lautadev.juegos_deportivos.model.Category;
import com.lautadev.juegos_deportivos.repository.ICategoryRepository;
import com.lautadev.juegos_deportivos.util.NullAwareBeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService implements ICategoryService{
    @Autowired
    private ICategoryRepository categoryRepository;

    @Override
    public void saveCategory(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Optional<Category> findCategory(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public void editCategory(Long id,Category category) {
        Category categoryEdit = this.findCategory(id).orElse(null);

        NullAwareBeanUtils.copyNonNullProperties(category,categoryEdit);

        assert categoryEdit != null;
        categoryRepository.save(categoryEdit);
    }
}
