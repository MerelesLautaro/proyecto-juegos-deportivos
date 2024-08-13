package com.lautadev.juegos_deportivos.service;

import com.lautadev.juegos_deportivos.DataProviderPermission;
import com.lautadev.juegos_deportivos.model.Permission;
import com.lautadev.juegos_deportivos.repository.IPermissionRepository;
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
class PermissionServiceTest {

    @Mock
    private IPermissionRepository permissionRepository;

    @InjectMocks
    private PermissionService permissionService;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("Prueba para guardar un recurso")
    void testSavePermission() {
        Permission permission =  DataProviderPermission.newPermissionMock();

        permissionService.savePermission(permission);

        ArgumentCaptor<Permission> permissionArgumentCaptor = ArgumentCaptor.forClass(Permission.class);
        verify(this.permissionRepository).save(any(Permission.class));
        verify(this.permissionRepository).save(permissionArgumentCaptor.capture());
        assertEquals("UPGRADE",permissionArgumentCaptor.getValue().getPermission());
    }

    @Test
    @DisplayName("Prueba obtener todos los recursos")
    void testGetPermission() {
        when(permissionRepository.findAll()).thenReturn(DataProviderPermission.permissionsListMock());

        List<Permission> permissionList = permissionService.getPermission();

        assertNotNull(permissionList);
        assertFalse(permissionList.isEmpty());
        assertEquals("CREATE",permissionList.get(0).getPermission());
    }

    @Test
    @DisplayName("Prueba en caso de encontrar el recurso solicitado por ID")
    void testFindPermission() {
        Long id = 1L;
        when(permissionRepository.findById(id)).thenReturn(Optional.of(DataProviderPermission.permissionMock()));
        Optional<Permission> permission = permissionService.findPermission(1L);

        assertNotNull(permission);
        assertTrue(permission.isPresent());
        assertEquals("CREATE",permission.get().getPermission());
    }

    @Test
    @DisplayName("Prueba en caso de NO encontrar el recurso solicitado por ID")
    void testFindPermissionNotFound(){
        when(permissionRepository.findById(1L)).thenReturn(DataProviderPermission.permissionMockNotFound());
        Optional<Permission> permission = permissionService.findPermission(1L);

        assertFalse(permission.isPresent());
        assertEquals(Optional.empty(),permission);
        assertNotNull(permission);
    }

    @Test
    @DisplayName("Prueba borrar un recurso por ID")
    void testDeletePermission() {
        permissionService.deletePermission(1L);

        ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(this.permissionRepository).deleteById(anyLong());
        verify(this.permissionRepository).deleteById(longArgumentCaptor.capture());
        assertEquals(1L,longArgumentCaptor.getValue());
    }

    @Test
    @DisplayName("Prueba para editar un recurso")
    void testEditPermission() {
        Permission permission = DataProviderPermission.newPermissionMock();

        permissionService.editPermission(permission);

        ArgumentCaptor<Permission> permissionArgumentCaptor = ArgumentCaptor.forClass(Permission.class);
        verify(this.permissionRepository).save(any(Permission.class));
        verify(this.permissionRepository).save(permissionArgumentCaptor.capture());
        assertEquals("UPGRADE", permissionArgumentCaptor.getValue().getPermission());
    }
}