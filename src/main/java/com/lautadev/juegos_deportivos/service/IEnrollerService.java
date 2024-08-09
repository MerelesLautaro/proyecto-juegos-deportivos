package com.lautadev.juegos_deportivos.service;

import com.lautadev.juegos_deportivos.dto.EnrollerDTO;
import com.lautadev.juegos_deportivos.model.Enroller;

import java.util.List;
import java.util.Optional;

public interface IEnrollerService {
    public void saveEnroller(Enroller enroller);
    public List<EnrollerDTO> getEnrollers();
    public Optional<EnrollerDTO> findEnroller(Long id);
    public void deleteEnroller(Long id);
    public Optional<EnrollerDTO> editEnroller(Long id, Enroller enroller);
}
