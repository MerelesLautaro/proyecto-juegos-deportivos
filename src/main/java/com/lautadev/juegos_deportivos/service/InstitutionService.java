package com.lautadev.juegos_deportivos.service;

import com.lautadev.juegos_deportivos.model.Institution;
import com.lautadev.juegos_deportivos.repository.IInstitutionRepository;
import com.lautadev.juegos_deportivos.util.NullAwareBeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InstitutionService implements IInstitutionService {
    @Autowired
    private IInstitutionRepository institutionRepository;

    @Override
    public void saveInstitution(Institution institution) {
        institutionRepository.save(institution);
    }

    @Override
    public List<Institution> getInstitutions() {
        return institutionRepository.findAll();
    }

    @Override
    public Optional<Institution> findInstitution(Long id) {
        return institutionRepository.findById(id);
    }

    @Override
    public void deleteInstitution(Long id) {
        institutionRepository.deleteById(id);
    }

    @Override
    public Institution editInstitution(Long id,Institution institution) {
        Institution institutionEdit = this.findInstitution(id).orElse(null);

        NullAwareBeanUtils.copyNonNullProperties(institution,institutionEdit);

        assert institutionEdit != null;
        return institutionRepository.save(institutionEdit);
    }
}
