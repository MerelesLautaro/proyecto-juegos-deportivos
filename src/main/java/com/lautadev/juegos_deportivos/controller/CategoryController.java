package com.lautadev.juegos_deportivos.controller;

import com.lautadev.juegos_deportivos.model.Category;
import com.lautadev.juegos_deportivos.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/category")
@PreAuthorize("permitAll()")
public class CategoryController {
    @Autowired
    private ICategoryService categoryService;

    @PostMapping("/save")
    public ResponseEntity<String> saveCategory(@RequestBody Category category){
        categoryService.saveCategory(category);
        return ResponseEntity.ok("Category saved successfully");
    }

    @GetMapping("/get")
    public ResponseEntity<List<Category>> getCategories(){
        return ResponseEntity.ok(categoryService.getCategories());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Category> findCategory(@PathVariable Long id){
        Optional<Category> category =  categoryService.findCategory(id);
        return category.map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id){
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("Category deleted");
    }

    @PatchMapping("/edit/{id}")
    public ResponseEntity<Category> editCategory(@PathVariable Long id,@RequestBody Category category){
        return ResponseEntity.ok(categoryService.editCategory(id,category));
    }
}
