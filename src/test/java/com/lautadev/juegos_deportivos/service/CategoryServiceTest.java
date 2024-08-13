package com.lautadev.juegos_deportivos.service;

import com.lautadev.juegos_deportivos.model.Category;
import com.lautadev.juegos_deportivos.repository.ICategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private ICategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("Prueba guardar una categoría")
    void testSaveCategory() {
        Category category = new Category();
        category.setName("Test Category");

        // Llamada al método
        categoryService.saveCategory(category);

        // Verificaciones
        ArgumentCaptor<Category> categoryArgumentCaptor = ArgumentCaptor.forClass(Category.class);
        verify(categoryRepository).save(categoryArgumentCaptor.capture());
        assertEquals("Test Category", categoryArgumentCaptor.getValue().getName());
    }

    @Test
    @DisplayName("Prueba obtener todas las categorías")
    void testGetCategories() {
        List<Category> categories = List.of(
                new Category(1L, "Category 1"),
                new Category(2L, "Category 2")
        );
        when(categoryRepository.findAll()).thenReturn(categories);

        List<Category> result = categoryService.getCategories();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Category 1", result.get(0).getName());
    }

    @Test
    @DisplayName("Prueba encontrar una categoría por ID")
    void testFindCategory() {
        Long id = 1L;
        Category category = new Category(id, "Category 1");
        when(categoryRepository.findById(id)).thenReturn(Optional.of(category));

        Optional<Category> result = categoryService.findCategory(id);

        assertTrue(result.isPresent());
        assertEquals("Category 1", result.get().getName());
    }

    @Test
    @DisplayName("Prueba encontrar una categoría por ID no existente")
    void testFindCategoryNotFound() {
        Long id = 1L;
        when(categoryRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Category> result = categoryService.findCategory(id);

        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("Prueba borrar una categoría por ID")
    void testDeleteCategory() {
        Long id = 1L;

        // Llamada al método
        categoryService.deleteCategory(id);

        // Verificaciones
        ArgumentCaptor<Long> idArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(categoryRepository).deleteById(idArgumentCaptor.capture());
        assertEquals(id, idArgumentCaptor.getValue());
    }

    @Test
    @DisplayName("Prueba editar una categoría")
    void testEditCategory() {
        Long id = 1L;
        Category existingCategory = new Category(id, "Old Category");
        Category updatedCategory = new Category(null, "New Category");

        when(categoryRepository.findById(id)).thenReturn(Optional.of(existingCategory));
        when(categoryRepository.save(any(Category.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Category result = categoryService.editCategory(id, updatedCategory);

        ArgumentCaptor<Category> categoryArgumentCaptor = ArgumentCaptor.forClass(Category.class);
        verify(categoryRepository).save(categoryArgumentCaptor.capture());

        Category savedCategory = categoryArgumentCaptor.getValue();
        assertEquals("New Category", savedCategory.getName());
        assertEquals(id, savedCategory.getId());
        assertEquals("New Category", result.getName());
    }

}