package com.lautadev.juegos_deportivos.service;

import com.lautadev.juegos_deportivos.dto.InscriptionDTO;
import com.lautadev.juegos_deportivos.model.Inscription;
import com.lautadev.juegos_deportivos.repository.IInscriptionRepository;
import com.lautadev.juegos_deportivos.util.NullAwareBeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InscriptionService implements IInscriptionService {
    @Autowired
    private IInscriptionRepository inscriptionRepository;

    @Override
    public void saveInscription(Inscription inscription) {
        inscriptionRepository.save(inscription);
    }

    @Override
    public List<InscriptionDTO> getInscriptions() {
        List<Inscription> inscriptionList = inscriptionRepository.findAll();
        List<InscriptionDTO> inscriptionDTOS = new ArrayList<>();

        for(Inscription inscription: inscriptionList){
            inscriptionDTOS.add(InscriptionDTO.fromInscription(inscription));
        }

        return inscriptionDTOS;

    }

    @Override
    public Optional<Inscription> findInscription(Long id) {
        return inscriptionRepository.findById(id);
    }

    @Override
    public void deleteInscription(Long id) {
        inscriptionRepository.deleteById(id);
    }

    @Override
    public Inscription editInscription(Long id,Inscription inscription) {
        Inscription inscriptionEdit = this.findInscription(id).orElse(null);

        NullAwareBeanUtils.copyNonNullProperties(inscription,inscriptionEdit);

        assert inscriptionEdit != null;
        return inscriptionRepository.save(inscriptionEdit);
    }

    @Override
    public InscriptionDTO findInscriptionDTO(Long id) {
        Inscription inscription = this.findInscription(id).orElse(null);
        return InscriptionDTO.fromInscription(inscription);
    }
}
