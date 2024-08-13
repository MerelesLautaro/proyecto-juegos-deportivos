package com.lautadev.juegos_deportivos.service;

import com.lautadev.juegos_deportivos.model.Account;
import com.lautadev.juegos_deportivos.model.Enroller;
import com.lautadev.juegos_deportivos.model.Permission;
import com.lautadev.juegos_deportivos.model.Role;
import com.lautadev.juegos_deportivos.repository.IAccountRepository;
import com.lautadev.juegos_deportivos.repository.IEnrollerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private IAccountRepository accountRepository;

    @Mock
    private IRoleService roleService;

    @Mock
    private IEnrollerRepository enrollerRepository;

    @Mock
    private IUserDetailsService userDetailsService;

    @InjectMocks
    private AccountService accountService;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("Prueba para guardar una cuenta con roles válidos")
    void testSaveAccount() {
        Permission createPermission = new Permission(1L, "CREATE");
        Permission readPermission = new Permission(2L, "READ");

        Account account = new Account();
        account.setPassword("password123");
        account.setRoleList(Set.of(new Role(1L, "USER",new HashSet<>(Set.of(readPermission)))));

        Role role = new Role(1L, "USER",new HashSet<>(Set.of(createPermission, readPermission)));
        when(roleService.findRole(1L)).thenReturn(Optional.of(role));
        when(accountRepository.save(any(Account.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Account savedAccount = accountService.saveAccount(account);

        ArgumentCaptor<Account> accountArgumentCaptor = ArgumentCaptor.forClass(Account.class);
        verify(accountRepository).save(accountArgumentCaptor.capture());
        assertTrue(accountArgumentCaptor.getValue().getRoleList().contains(role));
    }

    @Test
    @DisplayName("Prueba guardar una cuenta con lista de roles vacía")
    void testSaveAccountWithEmptyRoleList() {
        Account account = new Account();
        account.setPassword("test");
        account.setRoleList(Set.of()); // Lista de roles vacía

        lenient().when(accountRepository.save(any(Account.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Account result = accountService.saveAccount(account);

        verify(accountRepository, never()).save(any(Account.class));

        assertEquals(account, result);
        assertTrue(result.getRoleList().isEmpty());
    }

    @Test
    @DisplayName("Prueba obtener todas las cuentas")
    void testGetAccounts() {
        List<Account> accounts = List.of(new Account(), new Account());
        when(accountRepository.findAll()).thenReturn(accounts);

        List<Account> result = accountService.getAccounts();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("Prueba encontrar una cuenta por ID")
    void testFindAccount() {
        Account account = new Account();
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        Optional<Account> result = accountService.findAccount(1L);

        assertTrue(result.isPresent());
        assertEquals(account, result.get());
    }

    @Test
    @DisplayName("Prueba encontrar una cuenta por ID no existente")
    void testFindAccountNotFound() {
        when(accountRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Account> result = accountService.findAccount(1L);

        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("Prueba borrar una cuenta sin rol ADMIN y siendo el enroller")
    void testDeleteAccountAsEnroller() {
        Account account = new Account();
        account.setId(1L);

        Enroller enroller = new Enroller();
        enroller.setAccount(account);

        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(enrollerRepository.findById(anyLong())).thenReturn(Optional.of(enroller));
        when(userDetailsService.hasRoleAdmin()).thenReturn(false);
        when(userDetailsService.getCurrentEnrollerId()).thenReturn(1L);

        accountService.deleteAccount(1L);

        verify(accountRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Prueba borrar una cuenta sin permisos suficientes")
    void testDeleteAccountAccessDenied() {
        Account account = new Account();
        account.setId(1L);

        Enroller enroller = new Enroller();
        enroller.setAccount(new Account()); // No coincide con la cuenta a borrar

        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(enrollerRepository.findById(anyLong())).thenReturn(Optional.of(enroller));
        when(userDetailsService.hasRoleAdmin()).thenReturn(false);
        when(userDetailsService.getCurrentEnrollerId()).thenReturn(2L);

        assertThrows(AccessDeniedException.class, () -> accountService.deleteAccount(1L));
    }

    @Test
    @DisplayName("Prueba editar una cuenta sin permisos suficientes")
    void testEditAccountAccessDenied() {
        Account existingAccount = new Account();
        existingAccount.setId(1L);

        Account updatedAccount = new Account();
        updatedAccount.setId(1L);
        updatedAccount.setUsername("updatedUsername");

        Enroller enroller = new Enroller();
        enroller.setAccount(new Account()); // No coincide con la cuenta a editar

        when(accountRepository.findById(1L)).thenReturn(Optional.of(existingAccount));
        when(enrollerRepository.findById(anyLong())).thenReturn(Optional.of(enroller));
        when(userDetailsService.hasRoleAdmin()).thenReturn(false);
        when(userDetailsService.getCurrentEnrollerId()).thenReturn(2L);

        assertThrows(AccessDeniedException.class, () -> accountService.editAccount(1L, updatedAccount));
    }

    @Test
    @DisplayName("Prueba para encriptar la contraseña")
    void testEncriptPassword() {
        String rawPassword = "password123";
        String encodedPassword = accountService.encriptPassword(rawPassword);

        assertNotNull(encodedPassword);
        assertNotEquals(rawPassword, encodedPassword);
        assertTrue(new BCryptPasswordEncoder().matches(rawPassword, encodedPassword));
    }
}