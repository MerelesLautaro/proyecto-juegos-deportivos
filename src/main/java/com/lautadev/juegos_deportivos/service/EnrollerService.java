package com.lautadev.juegos_deportivos.service;

import com.lautadev.juegos_deportivos.dto.EnrollerDTO;
import com.lautadev.juegos_deportivos.model.Enroller;
import com.lautadev.juegos_deportivos.repository.IEnrollerRepository;
import com.lautadev.juegos_deportivos.util.NullAwareBeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EnrollerService implements IEnrollerService {
    @Autowired
    private IEnrollerRepository enrollerRepository;

    @Override
    public void saveEnroller(Enroller enroller) {
        enrollerRepository.save(enroller);
    }

    @Override
    public List<EnrollerDTO> getEnrollers() {
        List<Enroller> enrollerList = enrollerRepository.findAll();
        List<EnrollerDTO> enrollerDTOS = new ArrayList<>();

        for(Enroller enroller:enrollerList){
            enrollerDTOS.add(EnrollerDTO.fromEnroller(enroller));
        }

        return enrollerDTOS;
    }

    @Override
    public Optional<EnrollerDTO> findEnroller(Long id) {
        Enroller enroller = enrollerRepository.findById(id).orElse(null);
        return Optional.ofNullable(EnrollerDTO.fromEnroller(enroller));
    }

    @Override
    public void deleteEnroller(Long id) {
        enrollerRepository.deleteById(id);
    }

    @Override
    public Optional<EnrollerDTO> editEnroller(Long id, Enroller enroller) {
        Enroller enrollerEdit = enrollerRepository.findById(id).orElse(null);

        NullAwareBeanUtils.copyNonNullProperties(enroller,enrollerEdit);

        assert enrollerEdit != null;
        enrollerRepository.save(enrollerEdit);

        return this.findEnroller(enrollerEdit.getId());
    }
}
