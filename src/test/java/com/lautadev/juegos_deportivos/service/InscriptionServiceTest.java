package com.lautadev.juegos_deportivos.service;

import com.lautadev.juegos_deportivos.dto.InscriptionDTO;
import com.lautadev.juegos_deportivos.model.Discipline;
import com.lautadev.juegos_deportivos.model.Enroller;
import com.lautadev.juegos_deportivos.model.Inscription;
import com.lautadev.juegos_deportivos.model.Institution;
import com.lautadev.juegos_deportivos.model.enums.Extract;
import com.lautadev.juegos_deportivos.model.enums.Gender;
import com.lautadev.juegos_deportivos.repository.IInscriptionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InscriptionServiceTest {

    @Mock
    private IInscriptionRepository inscriptionRepository;

    @InjectMocks
    private InscriptionService inscriptionService;

    @Mock
    private IUserDetailsService userDetailsService;


    @Test
    @DisplayName("Prueba guardar una inscripción")
    void testSaveInscription() {
        Inscription inscription = new Inscription();
        inscription.setId(1L);
        inscription.setInscriptionDate(LocalDateTime.now());
        inscription.setGender(Gender.MASCULINO);
        inscription.setExtract(Extract.ADULTO);
        inscription.setParticipants(new ArrayList<>());
        inscription.setDiscipline(new Discipline());
        inscription.setEnroller(new Enroller());
        inscription.setInstitution(new Institution());

        inscriptionService.saveInscription(inscription);

        verify(inscriptionRepository).save(inscription);
    }

    @Test
    @DisplayName("Prueba obtener todas las inscripciones")
    void testGetInscriptions() {
        List<Inscription> inscriptions = Arrays.asList(
                new Inscription(1L, LocalDateTime.now(), Gender.MASCULINO, Extract.ADULTO, new ArrayList<>(), new Discipline(), new Enroller(), new Institution())
        );

        when(inscriptionRepository.findAll()).thenReturn(inscriptions);

        List<InscriptionDTO> result = inscriptionService.getInscriptions();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(inscriptions.get(0).getId(), result.get(0).getId());
    }

    @Test
    @DisplayName("Prueba encontrar una inscripción por ID")
    void testFindInscription() {
        Long id = 1L;
        Inscription inscription = new Inscription();
        inscription.setId(id);

        when(inscriptionRepository.findById(id)).thenReturn(Optional.of(inscription));

        Optional<Inscription> result = inscriptionService.findInscription(id);

        assertTrue(result.isPresent());
        assertEquals(id, result.get().getId());
    }

    @Test
    @DisplayName("Prueba eliminar una inscripción con autorización")
    void testDeleteInscriptionWithAuthorization() {
        Long id = 1L;
        Long enrollerId = 1L;
        Inscription inscription = new Inscription();
        inscription.setId(id);
        inscription.setEnroller(new Enroller());
        inscription.getEnroller().setId(enrollerId);

        when(inscriptionRepository.findById(id)).thenReturn(Optional.of(inscription));
        when(userDetailsService.getCurrentEnrollerId()).thenReturn(enrollerId);
        when(userDetailsService.hasRoleAdmin()).thenReturn(false);

        inscriptionService.deleteInscription(id);

        verify(inscriptionRepository).deleteById(id);
    }

    @Test
    @DisplayName("Prueba editar una inscripción con autorización")
    void testEditInscriptionWithAuthorization() {
        Long id = 1L;
        Long enrollerId = 1L;
        Inscription existingInscription = new Inscription();
        existingInscription.setId(id);
        existingInscription.setEnroller(new Enroller());
        existingInscription.getEnroller().setId(enrollerId);

        Inscription updatedInscription = new Inscription();
        updatedInscription.setId(id);
        updatedInscription.setInscriptionDate(LocalDateTime.now());

        when(inscriptionRepository.findById(id)).thenReturn(Optional.of(existingInscription));
        when(userDetailsService.getCurrentEnrollerId()).thenReturn(enrollerId);
        when(userDetailsService.hasRoleAdmin()).thenReturn(false);
        when(inscriptionRepository.save(any(Inscription.class))).thenAnswer(invocation -> invocation.getArgument(0));

        InscriptionDTO result = inscriptionService.editInscription(id, updatedInscription);

        ArgumentCaptor<Inscription> insCaptor = ArgumentCaptor.forClass(Inscription.class);
        verify(inscriptionRepository).save(insCaptor.capture());

        Inscription savedInscription = insCaptor.getValue();
        assertNotNull(savedInscription);
        assertEquals(id, savedInscription.getId());
        assertEquals(updatedInscription.getInscriptionDate(), savedInscription.getInscriptionDate());
        assertEquals(updatedInscription.getGender(), savedInscription.getGender()); // Verifica todos los campos relevantes
        assertEquals(updatedInscription.getExtract(), savedInscription.getExtract());

        assertNotNull(result);
        assertEquals(id, result.getId());
    }

    @Test
    @DisplayName("Prueba encontrar una inscripción DTO por ID")
    void testFindInscriptionDTO() {
        Long id = 1L;
        Inscription inscription = new Inscription();
        inscription.setId(id);

        when(inscriptionRepository.findById(id)).thenReturn(Optional.of(inscription));

        InscriptionDTO result = inscriptionService.findInscriptionDTO(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
    }

    @Test
    @DisplayName("Prueba encontrar inscripciones DTO por DNI")
    void testFindInscriptionDTOByDni() {
        String dni = "123456";
        List<Inscription> inscriptions = Arrays.asList(
                new Inscription(1L, LocalDateTime.now(), Gender.MASCULINO, Extract.INFANTIL, new ArrayList<>(), new Discipline(), new Enroller(), new Institution())
        );

        when(inscriptionRepository.findByParticipantDni(dni)).thenReturn(inscriptions);

        List<InscriptionDTO> result = inscriptionService.findInscriptionDTOByDni(dni);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(inscriptions.get(0).getId(), result.get(0).getId());
    }

}