package com.lautadev.juegos_deportivos.service;

import com.lautadev.juegos_deportivos.dto.InscriptionDTO;
import com.lautadev.juegos_deportivos.model.Inscription;
import com.lautadev.juegos_deportivos.repository.IInscriptionRepository;
import com.lautadev.juegos_deportivos.util.NullAwareBeanUtils;
import com.lautadev.juegos_deportivos.util.PDFGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InscriptionService implements IInscriptionService {
    @Autowired
    private IInscriptionRepository inscriptionRepository;

    @Autowired
    private IUserDetailsService userDetailsService;

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
        Inscription inscription = inscriptionRepository.findById(id).orElse(null);
        Long enrollerId = userDetailsService.getCurrentEnrollerId();
        if (!inscription.getEnroller().getId().equals(enrollerId)) {
            throw new AccessDeniedException("You are not authorized to delete this inscription");
        }
        inscriptionRepository.deleteById(id);
    }

    @Override
    public InscriptionDTO editInscription(Long id,Inscription inscription) {
        Inscription inscriptionEdit = this.findInscription(id).orElse(null);
        Long enrollerId = userDetailsService.getCurrentEnrollerId();
        if (!inscriptionEdit.getEnroller().getId().equals(enrollerId)) {
            throw new AccessDeniedException("You are not authorized to edit this inscription");
        }

        NullAwareBeanUtils.copyNonNullProperties(inscription,inscriptionEdit);

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
