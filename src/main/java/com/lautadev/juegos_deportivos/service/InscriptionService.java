package com.lautadev.juegos_deportivos.service;

import com.lautadev.juegos_deportivos.model.Inscription;
import com.lautadev.juegos_deportivos.repository.IInscriptionRepository;
import com.lautadev.juegos_deportivos.util.NullAwareBeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<Inscription> getInscriptions() {
        return inscriptionRepository.findAll();
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
    public void editInscription(Long id,Inscription inscription) {
        Inscription inscriptionEdit = this.findInscription(id).orElse(null);

        NullAwareBeanUtils.copyNonNullProperties(inscription,inscriptionEdit);

        assert inscriptionEdit != null;
        inscriptionRepository.save(inscriptionEdit);
    }
}