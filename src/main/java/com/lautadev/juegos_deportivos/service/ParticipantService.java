package com.lautadev.juegos_deportivos.service;

import com.lautadev.juegos_deportivos.model.Participant;
import com.lautadev.juegos_deportivos.repository.IParticipantRepository;
import com.lautadev.juegos_deportivos.util.NullAwareBeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParticipantService implements IParticipantService{
    @Autowired
    private IParticipantRepository participantRepository;

    @Override
    public void saveParticipant(Participant participant) {
        participantRepository.save(participant);
    }

    @Override
    public List<Participant> getParticipants() {
        return participantRepository.findAll();
    }

    @Override
    public Optional<Participant> findParticipant(Long id) {
        return participantRepository.findById(id);
    }

    @Override
    public void deleteParticipant(Long id) {
        participantRepository.deleteById(id);
    }

    @Override
    public Participant editParticipant(Long id,Participant participant) {
        Participant participantEdit = this.findParticipant(id).orElse(null);

        NullAwareBeanUtils.copyNonNullProperties(participant,participantEdit);

        assert participantEdit != null;
        return participantRepository.save(participantEdit);
    }
}
