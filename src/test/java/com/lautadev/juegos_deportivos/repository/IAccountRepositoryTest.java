package com.lautadev.juegos_deportivos.repository;

import com.lautadev.juegos_deportivos.model.Account;
import com.lautadev.juegos_deportivos.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IAccountRepositoryTest {

    @Mock
    private IAccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    private Account testAccount;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testAccount = Account.builder()
                .id(1L)
                .username("testUser")
                .password("testPass")
                .enabled(true)
                .accountNotExpired(true)
                .accountNotLocked(true)
                .credentialNotExpired(true)
                .roleList(new HashSet<>())
                .build();

        lenient().when(accountRepository.findUserEntityByUsername("testUser")).thenReturn(Optional.of(testAccount));
    }

    @Test
    void testFindUserEntityByUsername() {
        Optional<Account> result = accountRepository.findUserEntityByUsername("testUser");

        assertTrue(result.isPresent());
        assertEquals("testUser", result.get().getUsername());
    }

    @Test
    public void testFindUserEntityByUsernameNotFound() {
        when(accountRepository.findUserEntityByUsername("testUserNotFound")).thenReturn(Optional.empty());

        Optional<Account> result = accountRepository.findUserEntityByUsername("testUserNotFound");

        assertFalse(result.isPresent());
    }
}