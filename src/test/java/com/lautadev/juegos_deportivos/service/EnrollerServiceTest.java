package com.lautadev.juegos_deportivos.service;

import com.lautadev.juegos_deportivos.dto.EnrollerDTO;
import com.lautadev.juegos_deportivos.model.Account;
import com.lautadev.juegos_deportivos.model.Enroller;
import com.lautadev.juegos_deportivos.repository.IEnrollerRepository;
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
class EnrollerServiceTest {

    @Mock
    private IEnrollerRepository enrollerRepository;

    @Mock
    private IUserDetailsService userDetailsService;

    @InjectMocks
    private EnrollerService enrollerService;

    @Test
    @DisplayName("Prueba guardar un enroller")
    void testSaveEnroller() {
        Enroller enroller = new Enroller();
        enroller.setId(1L);

        enrollerService.saveEnroller(enroller);

        ArgumentCaptor<Enroller> enrollerArgumentCaptor = ArgumentCaptor.forClass(Enroller.class);
        verify(enrollerRepository).save(enrollerArgumentCaptor.capture());
        assertEquals(1L, enrollerArgumentCaptor.getValue().getId());
    }

    @Test
    @DisplayName("Prueba obtener todos los enrollers")
    void testGetEnrollers() {
        List<Enroller> enrollers = new ArrayList<>();
        Enroller enroller1 = new Enroller();
        enroller1.setDni("430984894");
        Enroller enroller2 = new Enroller();
        enroller2.setDni("430984894");

        enrollers.add(enroller1);
        enrollers.add(enroller2);

        List<EnrollerDTO> enrollerDTOs = enrollers.stream()
                .map(EnrollerDTO::fromEnroller)
                .collect(Collectors.toList());

        when(enrollerRepository.findAll()).thenReturn(enrollers);

        List<EnrollerDTO> result = enrollerService.getEnrollers();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("430984894", result.get(0).getDni());
    }

    @Test
    @DisplayName("Prueba encontrar un enroller por ID")
    void testFindEnroller() {
        Long id = 1L;
        Enroller enroller = new Enroller();
        enroller.setId(id);
        enroller.setParticipantList(new ArrayList<>()); // Inicializa la lista para evitar NullPointerException

        EnrollerDTO enrollerDTO = EnrollerDTO.fromEnroller(enroller);

        when(enrollerRepository.findById(id)).thenReturn(Optional.of(enroller));

        Optional<EnrollerDTO> result = enrollerService.findEnroller(id);

        assertTrue(result.isPresent());
        assertEquals(id, result.get().getId());
    }

    @Test
    @DisplayName("Prueba encontrar un enroller que no existe por ID")
    void testFindEnrollerNotFound() {
        Long id = 1L;

        when(enrollerRepository.findById(id)).thenReturn(Optional.empty());

        Optional<EnrollerDTO> result = enrollerService.findEnroller(id);

        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("Prueba borrar un enroller con autorización")
    void testDeleteEnrollerWithAuthorization() {
        Long id = 1L;
        Long currentEnrollerId = 1L;
        Enroller enroller = new Enroller();
        enroller.setId(id);

        when(enrollerRepository.findById(id)).thenReturn(Optional.of(enroller));
        when(userDetailsService.getCurrentEnrollerId()).thenReturn(currentEnrollerId);
        when(userDetailsService.hasRoleAdmin()).thenReturn(false);

        enrollerService.deleteEnroller(id);

        verify(enrollerRepository).deleteById(id);
    }

    @Test
    @DisplayName("Prueba borrar un enroller sin autorización")
    void testDeleteEnrollerWithoutAuthorization() {
        Long id = 1L;
        Long currentEnrollerId = 2L;
        Enroller enroller = new Enroller();
        enroller.setId(1L);

        when(enrollerRepository.findById(id)).thenReturn(Optional.of(enroller));
        when(userDetailsService.getCurrentEnrollerId()).thenReturn(currentEnrollerId);
        when(userDetailsService.hasRoleAdmin()).thenReturn(false);

        assertThrows(AccessDeniedException.class, () -> enrollerService.deleteEnroller(id));
    }

    @Test
    @DisplayName("Prueba editar un enroller con autorización")
    void testEditEnrollerWithAuthorization() {
        Long id = 1L;
        Long currentEnrollerId = 1L;

        Enroller existingEnroller = new Enroller();
        existingEnroller.setId(id);
        existingEnroller.setParticipantList(new ArrayList<>());

        Enroller updatedEnroller = new Enroller();
        updatedEnroller.setAccount(new Account());

        when(enrollerRepository.findById(id)).thenReturn(Optional.of(existingEnroller));
        when(userDetailsService.getCurrentEnrollerId()).thenReturn(currentEnrollerId);
        when(userDetailsService.hasRoleAdmin()).thenReturn(false);

        when(enrollerRepository.save(any(Enroller.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Optional<EnrollerDTO> result = enrollerService.editEnroller(id, updatedEnroller);

        ArgumentCaptor<Enroller> enrollerArgumentCaptor = ArgumentCaptor.forClass(Enroller.class);
        verify(enrollerRepository).save(enrollerArgumentCaptor.capture());

        Enroller savedEnroller = enrollerArgumentCaptor.getValue();
        assertEquals(id, savedEnroller.getId());
        assertEquals(updatedEnroller.getAccount(), savedEnroller.getAccount());

        assertTrue(result.isPresent());
        assertEquals(id, result.get().getId());
    }

    @Test
    @DisplayName("Prueba editar un enroller sin autorización")
    void testEditEnrollerWithoutAuthorization() {
        Long id = 1L;
        Long currentEnrollerId = 2L;
        Enroller existingEnroller = new Enroller();
        existingEnroller.setId(id);

        Enroller updatedEnroller = new Enroller();
        updatedEnroller.setAccount(new Account());

        when(enrollerRepository.findById(id)).thenReturn(Optional.of(existingEnroller));
        when(userDetailsService.getCurrentEnrollerId()).thenReturn(currentEnrollerId);
        when(userDetailsService.hasRoleAdmin()).thenReturn(false);

        assertThrows(AccessDeniedException.class, () -> enrollerService.editEnroller(id, updatedEnroller));
    }

}