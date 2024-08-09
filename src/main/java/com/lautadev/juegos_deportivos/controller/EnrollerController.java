package com.lautadev.juegos_deportivos.controller;

import com.lautadev.juegos_deportivos.dto.EnrollerDTO;
import com.lautadev.juegos_deportivos.model.Enroller;
import com.lautadev.juegos_deportivos.service.IEnrollerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/enroller")
public class EnrollerController {
    @Autowired
    private IEnrollerService enrollerService;

    @PostMapping("/save")
    public ResponseEntity<String> saveEnroller(@RequestBody Enroller enroller){
        enrollerService.saveEnroller(enroller);
        return ResponseEntity.ok("Enroller saved Successfully");
    }

    @GetMapping("/get")
    public ResponseEntity<List<EnrollerDTO>> getEnrollers(){
        return ResponseEntity.ok(enrollerService.getEnrollers());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<EnrollerDTO> findEnroller(@PathVariable Long id){
        Optional<EnrollerDTO> enroller = enrollerService.findEnroller(id);
        return enroller.map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEnroller(@PathVariable Long id){
        enrollerService.deleteEnroller(id);
        return ResponseEntity.ok("Enroller deleted");
    }

    @PatchMapping("/edit/{id}")
    public ResponseEntity<EnrollerDTO> editEnroller(@PathVariable Long id, @RequestBody Enroller enroller){
        Optional<EnrollerDTO> enrollerDTO =  enrollerService.editEnroller(id,enroller);
        return enrollerDTO.map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }
}
