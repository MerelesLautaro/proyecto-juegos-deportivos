package com.lautadev.juegos_deportivos.controller;

import com.lautadev.juegos_deportivos.dto.InscriptionDTO;
import com.lautadev.juegos_deportivos.model.Inscription;
import com.lautadev.juegos_deportivos.service.IInscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/inscription")
@PreAuthorize("permitAll()")
public class InscriptionController {
    @Autowired
    private IInscriptionService inscriptionService;

    @PostMapping("/save")
    public ResponseEntity<String> saveInscription(@RequestBody Inscription inscription){
        inscriptionService.saveInscription(inscription);
        return ResponseEntity.ok("Inscription saved successfully");
    }

    @GetMapping("/get")
    public ResponseEntity<List<InscriptionDTO>> getInscriptions(){
        return ResponseEntity.ok(inscriptionService.getInscriptions());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<InscriptionDTO> findInscription(@PathVariable Long id){
        return ResponseEntity.ok(inscriptionService.findInscriptionDTO(id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteInscription(@PathVariable Long id){
        inscriptionService.deleteInscription(id);
        return ResponseEntity.ok("Inscription deleted");
    }

    @PatchMapping("/edit/{id}")
    public ResponseEntity<Inscription> editInscription(@PathVariable Long id,@RequestBody Inscription inscription){
        return ResponseEntity.ok(inscriptionService.editInscription(id,inscription));
    }
}
