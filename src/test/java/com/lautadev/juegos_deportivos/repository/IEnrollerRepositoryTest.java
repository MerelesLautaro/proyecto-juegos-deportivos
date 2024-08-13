package com.lautadev.juegos_deportivos.repository;

import com.lautadev.juegos_deportivos.model.Account;
import com.lautadev.juegos_deportivos.model.Enroller;
import com.lautadev.juegos_deportivos.service.EnrollerService;
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
class IEnrollerRepositoryTest {

    @Mock
    private IEnrollerRepository enrollerRepository;

    @InjectMocks
    private EnrollerService enrollerService;

    private Enroller testEnroller;
    private Account testAccount;

    @BeforeEach
    void setUp(){
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

        testEnroller = Enroller.builder()
                .account(testAccount)
                .build();

        lenient().when(enrollerRepository.findByAccountId(1L)).thenReturn(Optional.of(testEnroller));
    }

    @Test
    void testFindByAccountId() {
        Optional<Enroller> result = enrollerRepository.findByAccountId(1L);

        assertTrue(result.isPresent());
    }

    @Test
    void testFindByAccountIdNotFound(){
        when(enrollerRepository.findByAccountId(1L)).thenReturn(Optional.empty());

        Optional<Enroller> result = enrollerRepository.findByAccountId(1L);

        assertFalse(result.isPresent());
    }
}