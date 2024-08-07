package com.lautadev.juegos_deportivos.controller;

import com.lautadev.juegos_deportivos.model.Discipline;
import com.lautadev.juegos_deportivos.service.IDisciplineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/discipline")
@PreAuthorize("permitAll()")
public class DisciplineController {
    @Autowired
    private IDisciplineService disciplineService;

    @PostMapping("/save")
    public ResponseEntity<String> saveDiscipline(@RequestBody Discipline discipline){
        disciplineService.saveDiscipline(discipline);
        return ResponseEntity.ok("Discipline saved successfully");
    }

    @GetMapping("/get")
    public ResponseEntity<List<Discipline>> getDisciplines(){
        return ResponseEntity.ok(disciplineService.getDisciplines());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Discipline> findDiscipline(@PathVariable Long id){
        Optional<Discipline> discipline = disciplineService.findDiscipline(id);
        return discipline.map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteDiscipline(@PathVariable Long id){
        disciplineService.deleteDiscipline(id);
        return ResponseEntity.ok("Discipline deleted");
    }

    @PatchMapping("/edit/{id}")
    public ResponseEntity<Discipline> editDiscipline(@PathVariable Long id,@RequestBody Discipline discipline){
        return ResponseEntity.ok(disciplineService.editDiscipline(id,discipline));
    }
}
