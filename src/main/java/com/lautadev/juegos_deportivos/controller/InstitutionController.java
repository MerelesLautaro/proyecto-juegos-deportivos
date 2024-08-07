package com.lautadev.juegos_deportivos.controller;

import com.lautadev.juegos_deportivos.model.Institution;
import com.lautadev.juegos_deportivos.service.IInstitutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/institution")
@PreAuthorize("permitAll()")
public class InstitutionController {
    @Autowired
    private IInstitutionService institutionService;

    @PostMapping("/save")
    public ResponseEntity<String> saveInstitution(@RequestBody Institution institution){
        institutionService.saveInstitution(institution);
        return ResponseEntity.ok("Institution saved successfully");
    }

    @GetMapping("/get")
    public ResponseEntity<List<Institution>> getInstitutions(){
        return ResponseEntity.ok(institutionService.getInstitutions());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Institution> findInstitution(@PathVariable Long id){
        Optional<Institution> institution = institutionService.findInstitution(id);
        return institution.map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteInstitution(@PathVariable Long id){
        institutionService.deleteInstitution(id);
        return ResponseEntity.ok("Institution deleted");
    }

    @PatchMapping("/edit/{id}")
    public ResponseEntity<Institution> editInstitution(@PathVariable Long id,@RequestBody Institution institution){
        return ResponseEntity.ok(institutionService.editInstitution(id,institution));
    }
}
