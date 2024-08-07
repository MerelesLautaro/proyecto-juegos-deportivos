package com.lautadev.juegos_deportivos.service;

import com.lautadev.juegos_deportivos.model.Participant;

import java.util.List;
import java.util.Optional;

public interface IParticipantService {
    public void saveParticipant(Participant participant);
    public List<Participant> getParticipants();
    public Optional<Participant> findParticipant(Long id);
    public void deleteParticipant(Long id);
    public Participant editParticipant(Long id,Participant participant);
}
