package com.lautadev.juegos_deportivos.service;

import com.lautadev.juegos_deportivos.model.Discipline;
import com.lautadev.juegos_deportivos.repository.IDisciplineRepository;
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
class DisciplineServiceTest {

    @Mock
    private IDisciplineRepository disciplineRepository;

    @InjectMocks
    private DisciplineService disciplineService;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("Prueba guardar una disciplina")
    void testSaveDiscipline() {
        Discipline discipline = new Discipline(null, "Basketball", "Team", null);

        disciplineService.saveDiscipline(discipline);

        ArgumentCaptor<Discipline> disciplineArgumentCaptor = ArgumentCaptor.forClass(Discipline.class);
        verify(disciplineRepository).save(disciplineArgumentCaptor.capture());
        assertEquals("Basketball", disciplineArgumentCaptor.getValue().getName());
    }

    @Test
    @DisplayName("Prueba obtener todas las disciplinas")
    void testGetDisciplines() {
        List<Discipline> disciplines = List.of(
                new Discipline(1L, "Basketball", "Team", null),
                new Discipline(2L, "Swimming", "Individual", null)
        );

        when(disciplineRepository.findAll()).thenReturn(disciplines);

        List<Discipline> result = disciplineService.getDisciplines();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(2, result.size());
        assertEquals("Basketball", result.get(0).getName());
    }

    @Test
    @DisplayName("Prueba encontrar una disciplina por ID")
    void testFindDiscipline() {
        Long id = 1L;
        Discipline discipline = new Discipline(id, "Basketball", "Team", null);

        when(disciplineRepository.findById(id)).thenReturn(Optional.of(discipline));

        Optional<Discipline> result = disciplineService.findDiscipline(id);

        assertTrue(result.isPresent());
        assertEquals("Basketball", result.get().getName());
    }

    @Test
    @DisplayName("Prueba encontrar una disciplina que no existe por ID")
    void testFindDisciplineNotFound() {
        Long id = 1L;

        when(disciplineRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Discipline> result = disciplineService.findDiscipline(id);

        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("Prueba borrar una disciplina por ID")
    void testDeleteDiscipline() {
        Long id = 1L;

        disciplineService.deleteDiscipline(id);

        verify(disciplineRepository).deleteById(id);
    }

    @Test
    @DisplayName("Prueba editar una disciplina")
    void testEditDiscipline() {
        Long id = 1L;
        Discipline existingDiscipline = new Discipline(id, "Basketball", "Team", null);
        Discipline updatedDiscipline = new Discipline(null, "Basketball Updated", "Individual", null);

        when(disciplineRepository.findById(id)).thenReturn(Optional.of(existingDiscipline));
        when(disciplineRepository.save(any(Discipline.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Discipline result = disciplineService.editDiscipline(id, updatedDiscipline);

        ArgumentCaptor<Discipline> disciplineArgumentCaptor = ArgumentCaptor.forClass(Discipline.class);
        verify(disciplineRepository).save(disciplineArgumentCaptor.capture());

        Discipline savedDiscipline = disciplineArgumentCaptor.getValue();
        assertEquals("Basketball Updated", savedDiscipline.getName());
        assertEquals("Individual", savedDiscipline.getModality());
        assertEquals(id, savedDiscipline.getId());
        assertEquals("Basketball Updated", result.getName());
    }
}