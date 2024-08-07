package com.lautadev.juegos_deportivos.controller;

import com.lautadev.juegos_deportivos.model.Participant;
import com.lautadev.juegos_deportivos.service.IParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/participant")
@PreAuthorize("permitAll()")
public class ParticipantController {
    @Autowired
    private IParticipantService  participantService;

    @PostMapping("/save")
    public ResponseEntity<String> saveParticipant(@RequestBody Participant participant){
        participantService.saveParticipant(participant);
        return ResponseEntity.ok("Participant saved successfully");
    }

    @GetMapping("/get")
    public ResponseEntity<List<Participant>> getParticipants(){
        return ResponseEntity.ok(participantService.getParticipants());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Participant> findParticipant(@PathVariable Long id){
        Optional<Participant> participant = participantService.findParticipant(id);
        return participant.map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteParticipant(@PathVariable Long id){
        participantService.deleteParticipant(id);
        return ResponseEntity.ok("Participant deleted");
    }

    @PatchMapping("/edit/{id}")
    public ResponseEntity<Participant> editParticipant(@PathVariable Long id,@RequestBody Participant participant){
        return ResponseEntity.ok(participantService.editParticipant(id,participant));
    }
}
