package com.lautadev.juegos_deportivos.service;

import com.lautadev.juegos_deportivos.dto.ParticipantDTO;
import com.lautadev.juegos_deportivos.model.Enroller;
import com.lautadev.juegos_deportivos.model.Participant;
import com.lautadev.juegos_deportivos.model.enums.SportRole;
import com.lautadev.juegos_deportivos.repository.IParticipantRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ParticipantServiceTest {

    @Mock
    private IParticipantRepository participantRepository;

    @Mock
    private IUserDetailsService userDetailsService;

    @InjectMocks
    private ParticipantService participantService;

    @Test
    @DisplayName("Prueba guardar un participante")
    void testSaveParticipant() {
        Participant participant = new Participant();
        participant.setId(1L);
        participant.setSportRole(SportRole.DEPORTISTA);

        participantService.saveParticipant(participant);

        ArgumentCaptor<Participant> participantArgumentCaptor = ArgumentCaptor.forClass(Participant.class);
        verify(participantRepository).save(participantArgumentCaptor.capture());
        assertEquals(1L, participantArgumentCaptor.getValue().getId());
        assertEquals(SportRole.DEPORTISTA, participantArgumentCaptor.getValue().getSportRole());
    }

    @Test
    @DisplayName("Prueba obtener todos los participantes")
    void testGetParticipants() {
        List<Participant> participants = new ArrayList<>();
        Participant participant1 = new Participant();
        participant1.setSportRole(SportRole.DEPORTISTA);
        Participant participant2 = new Participant();
        participant2.setSportRole(SportRole.RESPONSABLE_DE_EQUIPO);
        participants.add(participant1);
        participants.add(participant2);

        when(participantRepository.findAll()).thenReturn(participants);

        List<Participant> result = participantService.getParticipants();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(2, result.size());
        assertEquals(SportRole.RESPONSABLE_DE_EQUIPO, result.get(1).getSportRole());
    }

    @Test
    @DisplayName("Prueba encontrar un participante por ID")
    void testFindParticipant() {
        Long id = 1L;
        Participant participant = new Participant();
        participant.setId(id);

        when(participantRepository.findById(id)).thenReturn(Optional.of(participant));

        Optional<Participant> result = participantService.findParticipant(id);

        assertTrue(result.isPresent());
        assertEquals(id, result.get().getId());
    }

    @Test
    @DisplayName("Prueba encontrar un participante que no existe por ID")
    void testFindParticipantNotFound() {
        Long id = 1L;

        when(participantRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Participant> result = participantService.findParticipant(id);

        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("Prueba borrar un participante con autorización")
    void testDeleteParticipantWithAuthorization() {
        Long id = 1L;
        Long enrollerId = 2L;
        Participant participant = new Participant();
        participant.setId(id);
        Enroller enroller = new Enroller();
        enroller.setId(enrollerId);
        participant.setEnroller(enroller);

        when(participantRepository.findById(id)).thenReturn(Optional.of(participant));
        when(userDetailsService.getCurrentEnrollerId()).thenReturn(enrollerId);
        when(userDetailsService.hasRoleAdmin()).thenReturn(false);

        participantService.deleteParticipant(id);

        verify(participantRepository).deleteById(id);
    }

    @Test
    @DisplayName("Prueba borrar un participante sin autorización")
    void testDeleteParticipantWithoutAuthorization() {
        Long id = 1L;
        Long enrollerId = 2L;
        Participant participant = new Participant();
        participant.setId(id);
        Enroller enroller = new Enroller();
        enroller.setId(3L);  // Diferente al enrollerId
        participant.setEnroller(enroller);

        when(participantRepository.findById(id)).thenReturn(Optional.of(participant));
        when(userDetailsService.getCurrentEnrollerId()).thenReturn(enrollerId);
        when(userDetailsService.hasRoleAdmin()).thenReturn(false);

        assertThrows(AccessDeniedException.class, () -> participantService.deleteParticipant(id));
    }

    @Test
    @DisplayName("Prueba editar un participante con autorización")
    void testEditParticipantWithAuthorization() {
        Long id = 1L;
        Long enrollerId = 2L;
        Participant existingParticipant = new Participant();
        existingParticipant.setId(id);
        Enroller enroller = new Enroller();
        enroller.setId(enrollerId);
        existingParticipant.setEnroller(enroller);

        Participant updatedParticipant = new Participant();
        updatedParticipant.setSportRole(SportRole.INSTRUCTOR);

        when(participantRepository.findById(id)).thenReturn(Optional.of(existingParticipant));
        when(userDetailsService.getCurrentEnrollerId()).thenReturn(enrollerId);
        when(userDetailsService.hasRoleAdmin()).thenReturn(false);

        when(participantRepository.save(any(Participant.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ParticipantDTO result = participantService.editParticipant(id, updatedParticipant);

        ArgumentCaptor<Participant> participantArgumentCaptor = ArgumentCaptor.forClass(Participant.class);
        verify(participantRepository).save(participantArgumentCaptor.capture());

        Participant savedParticipant = participantArgumentCaptor.getValue();
        assertEquals(SportRole.INSTRUCTOR, savedParticipant.getSportRole());
        assertEquals(id, savedParticipant.getId());
        assertEquals(SportRole.INSTRUCTOR, result.getSportRole());
    }

    @Test
    @DisplayName("Prueba editar un participante sin autorización")
    void testEditParticipantWithoutAuthorization() {
        Long id = 1L;
        Long enrollerId = 2L;
        Participant existingParticipant = new Participant();
        existingParticipant.setId(id);
        Enroller enroller = new Enroller();
        enroller.setId(3L); // Diferente al enrollerId
        existingParticipant.setEnroller(enroller);

        Participant updatedParticipant = new Participant();
        updatedParticipant.setSportRole(SportRole.DEPORTISTA);

        when(participantRepository.findById(id)).thenReturn(Optional.of(existingParticipant));
        when(userDetailsService.getCurrentEnrollerId()).thenReturn(enrollerId);
        when(userDetailsService.hasRoleAdmin()).thenReturn(false);

        assertThrows(AccessDeniedException.class, () -> participantService.editParticipant(id, updatedParticipant));
    }

    @Test
    @DisplayName("Prueba encontrar un DTO de participante por ID")
    void testFindParticipantDTO() {
        Long id = 1L;
        Participant participant = new Participant();
        participant.setId(id);
        ParticipantDTO participantDTO = ParticipantDTO.fromParticipant(participant);

        when(participantRepository.findById(id)).thenReturn(Optional.of(participant));

        Optional<ParticipantDTO> result = participantService.findParticipantDTO(id);

        assertTrue(result.isPresent());
        assertEquals(id, result.get().getId());
    }

    @Test
    @DisplayName("Prueba encontrar un DTO de participante que no existe por ID")
    void testFindParticipantDTONotFound() {
        Long id = 1L;

        when(participantRepository.findById(id)).thenReturn(Optional.empty());

        Optional<ParticipantDTO> result = participantService.findParticipantDTO(id);

        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("Prueba obtener todos los DTOs de participantes")
    void testGetParticipantsDTO() {
        List<Participant> participants = new ArrayList<>();
        Participant participant1 = new Participant();
        participant1.setSportRole(SportRole.DEPORTISTA);
        Participant participant2 = new Participant();
        participant2.setSportRole(SportRole.RESPONSABLE_DE_EQUIPO);
        participants.add(participant1);
        participants.add(participant2);

        List<ParticipantDTO> participantDTOs = participants.stream()
                .map(ParticipantDTO::fromParticipant)
                .collect(Collectors.toList());

        when(participantRepository.findAll()).thenReturn(participants);

        List<ParticipantDTO> result = participantService.getParticipantsDTO();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(SportRole.DEPORTISTA, result.get(0).getSportRole());
    }

}