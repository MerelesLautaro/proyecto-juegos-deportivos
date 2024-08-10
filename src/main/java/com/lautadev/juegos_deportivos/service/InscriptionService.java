package com.lautadev.juegos_deportivos.service;

import com.lautadev.juegos_deportivos.dto.InscriptionDTO;
import com.lautadev.juegos_deportivos.model.Inscription;
import com.lautadev.juegos_deportivos.repository.IInscriptionRepository;
import com.lautadev.juegos_deportivos.util.NullAwareBeanUtils;
import com.lautadev.juegos_deportivos.util.PDFGenerator;
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
    public InscriptionDTO editInscription(Long id,Inscription inscription) {
        Inscription inscriptionEdit = this.findInscription(id).orElse(null);

        NullAwareBeanUtils.copyNonNullProperties(inscription,inscriptionEdit);

        assert inscriptionEdit != null;
        inscriptionRepository.save(inscriptionEdit);
        return this.findInscriptionDTO(inscriptionEdit.getId());
    }

    @Override
    public InscriptionDTO findInscriptionDTO(Long id) {
        Inscription inscription = this.findInscription(id).orElse(null);
        return InscriptionDTO.fromInscription(inscription);
    }

    @Override
    public List<InscriptionDTO> findInscriptionDTOByDni(String dni) {
        List<Inscription> inscriptionsList = inscriptionRepository.findByParticipantDni(dni);
        List<InscriptionDTO> inscriptionDTOS = new ArrayList<>();

        for(Inscription inscription: inscriptionsList){
            inscriptionDTOS.add(InscriptionDTO.fromInscription(inscription));
        }

        return inscriptionDTOS;
    }

    @Override
    public void generatePdfInscription(Long id) {
        InscriptionDTO inscriptionDTO = this.findInscriptionDTO(id);
        PDFGenerator.GeneratePdf(inscriptionDTO);
    }
}
