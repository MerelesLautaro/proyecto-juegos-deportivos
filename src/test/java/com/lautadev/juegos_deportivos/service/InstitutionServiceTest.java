package com.lautadev.juegos_deportivos.service;

import com.lautadev.juegos_deportivos.model.Institution;
import com.lautadev.juegos_deportivos.repository.IInstitutionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InstitutionServiceTest {

    @Mock
    private IInstitutionRepository institutionRepository;

    @InjectMocks
    private InstitutionService institutionService;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("Prueba guardar una institución")
    void testSaveInstitution() {
        Institution institution = new Institution(null, "ABC University", "123 Main St", null, "123-456-7890", null);

        institutionService.saveInstitution(institution);

        ArgumentCaptor<Institution> institutionArgumentCaptor = ArgumentCaptor.forClass(Institution.class);
        verify(institutionRepository).save(institutionArgumentCaptor.capture());
        assertEquals("ABC University", institutionArgumentCaptor.getValue().getName());
    }

    @Test
    @DisplayName("Prueba obtener todas las instituciones")
    void testGetInstitutions() {
        List<Institution> institutions = List.of(
                new Institution(1L, "ABC University", "123 Main St", null, "123-456-7890", null),
                new Institution(2L, "XYZ College", "456 Elm St", null, "987-654-3210",null )
        );

        when(institutionRepository.findAll()).thenReturn(institutions);

        List<Institution> result = institutionService.getInstitutions();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(2, result.size());
        assertEquals("ABC University", result.get(0).getName());
    }

    @Test
    @DisplayName("Prueba encontrar una institución por ID")
    void testFindInstitution() {
        Long id = 1L;
        Institution institution = new Institution(id, "ABC University", "123 Main St", null, "123-456-7890", null);

        when(institutionRepository.findById(id)).thenReturn(Optional.of(institution));

        Optional<Institution> result = institutionService.findInstitution(id);

        assertTrue(result.isPresent());
        assertEquals("ABC University", result.get().getName());
    }

    @Test
    @DisplayName("Prueba encontrar una institución que no existe por ID")
    void testFindInstitutionNotFound() {
        Long id = 1L;

        when(institutionRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Institution> result = institutionService.findInstitution(id);

        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("Prueba borrar una institución por ID")
    void testDeleteInstitution() {
        Long id = 1L;

        institutionService.deleteInstitution(id);

        verify(institutionRepository).deleteById(id);
    }

    @Test
    @DisplayName("Prueba editar una institución")
    void testEditInstitution() {
        Long id = 1L;
        Institution existingInstitution = new Institution(id, "ABC University", "123 Main St", null, "123-456-7890", null);
        Institution updatedInstitution = new Institution(null, "ABC University Updated", "789 Pine St", null, "321-654-9870", null);

        when(institutionRepository.findById(id)).thenReturn(Optional.of(existingInstitution));
        when(institutionRepository.save(any(Institution.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Institution result = institutionService.editInstitution(id, updatedInstitution);

        ArgumentCaptor<Institution> institutionArgumentCaptor = ArgumentCaptor.forClass(Institution.class);
        verify(institutionRepository).save(institutionArgumentCaptor.capture());

        Institution savedInstitution = institutionArgumentCaptor.getValue();
        assertEquals("ABC University Updated", savedInstitution.getName());
        assertEquals("789 Pine St", savedInstitution.getDomicile());
        assertEquals("321-654-9870", savedInstitution.getCel());
        assertEquals(id, savedInstitution.getId());
        assertEquals("ABC University Updated", result.getName());
    }
}