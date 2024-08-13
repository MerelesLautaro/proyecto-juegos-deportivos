package com.lautadev.juegos_deportivos.service;

import com.lautadev.juegos_deportivos.DataProviderRole;
import com.lautadev.juegos_deportivos.model.Permission;
import com.lautadev.juegos_deportivos.model.Role;
import com.lautadev.juegos_deportivos.repository.IRoleRepository;
import com.lautadev.juegos_deportivos.throwable.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

    @Mock
    private IRoleRepository  roleRepository;

    @Mock
    private IPermissionService permissionService;

    @InjectMocks
    private RoleService roleService;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("Prueba para guardar un recurso")
    void testSaveRoleWithAllValidPermissions() {
        Permission createPermission = new Permission(1L, "CREATE");
        Permission readPermission = new Permission(2L, "READ");

        Role role = new Role(1L, "ADMIN", new HashSet<>(Set.of(createPermission, readPermission)));

        when(permissionService.findPermission(1L)).thenReturn(Optional.of(createPermission));
        when(permissionService.findPermission(2L)).thenReturn(Optional.of(readPermission));
        when(roleRepository.save(any(Role.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Role savedRole = roleService.saveRole(role);

        assertNotNull(savedRole);
        assertEquals("ADMIN", savedRole.getRole());
        assertEquals(2, savedRole.getPermissionSet().size());
        assertTrue(savedRole.getPermissionSet().contains(createPermission));
        assertTrue(savedRole.getPermissionSet().contains(readPermission));
    }

    @Test
    @DisplayName("Prueba guardar un rol con algunos permisos válidos")
    void testSaveRoleWithSomeValidPermissions() {
        Permission createPermission = new Permission(1L, "CREATE");
        Permission invalidPermission = new Permission(2L, "INVALID");

        Role role = new Role(1L, "ADMIN", new HashSet<>(Set.of(createPermission, invalidPermission)));

        when(permissionService.findPermission(1L)).thenReturn(Optional.of(createPermission));
        when(permissionService.findPermission(2L)).thenReturn(Optional.empty()); // permiso no encontrado
        when(roleRepository.save(any(Role.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Role savedRole = roleService.saveRole(role);

        assertNotNull(savedRole);
        assertEquals("ADMIN", savedRole.getRole());
        assertEquals(1, savedRole.getPermissionSet().size());
        assertTrue(savedRole.getPermissionSet().contains(createPermission));
        assertFalse(savedRole.getPermissionSet().contains(invalidPermission));
    }

    @Test
    @DisplayName("Prueba obtener todos los recursos")
    void testGetRoles() {
        when(roleRepository.findAll()).thenReturn(DataProviderRole.roleListMock());

        List<Role> roleList  = roleService.getRoles();

        assertNotNull(roleList);
        assertFalse(roleList.isEmpty());
    }

    @Test
    @DisplayName("Prueba en caso de encontrar el recurso solicitado por ID")
    void testFindRole() {
        when(roleRepository.findById(1L)).thenReturn(DataProviderRole.roleMockOptional());
        Optional<Role> role = roleService.findRole(1L);

        assertNotNull(role);
        assertTrue(role.isPresent());
        assertEquals("ADMIN",role.get().getRole());
    }

    @Test
    @DisplayName("Prueba en caso de NO encontrar el recurso solicitado por ID")
    void testFinRoleNotFound(){
        when(roleRepository.findById(1L)).thenReturn(DataProviderRole.roleMockOptionalNotFound());
        Optional<Role> role = roleService.findRole(1L);

        assertFalse(role.isPresent());
        assertEquals(Optional.empty(),role);
        assertNotNull(role);
    }

    @Test
    @DisplayName("Prueba borrar un recurso por ID")
    void testDeleteRole() {
        roleService.deleteRole(1L);

        ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(this.roleRepository).deleteById(anyLong());
        verify(this.roleRepository).deleteById(longArgumentCaptor.capture());
        assertEquals(1L,longArgumentCaptor.getValue());
    }

    @Test
    @DisplayName("Prueba para editar un recurso  / not found")
    void testEditRoleNotFound() {
        Role updatedRole = DataProviderRole.newRoleMock();

        when(roleRepository.findById(4L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> roleService.editRole(4L, updatedRole));
        verify(roleRepository).findById(4L);
        verify(roleRepository, never()).save(any(Role.class));
    }
}