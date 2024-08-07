package com.lautadev.juegos_deportivos.service;

import com.lautadev.juegos_deportivos.model.Inscription;

import java.util.List;
import java.util.Optional;

public interface IInscriptionService {
    public void saveInscription(Inscription inscription);
    public List<Inscription> getInscriptions();
    public Optional<Inscription> findInscription(Long id);
    public void deleteInscription(Long id);
    public Inscription editInscription(Long id,Inscription inscription);
}
