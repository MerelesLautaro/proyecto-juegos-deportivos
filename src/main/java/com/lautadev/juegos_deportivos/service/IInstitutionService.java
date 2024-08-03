package com.lautadev.juegos_deportivos.service;


import com.lautadev.juegos_deportivos.model.Institution;

import java.util.List;
import java.util.Optional;

public interface IInstitutionService {
    public void saveInstitution(Institution institution);
    public List<Institution> getInstitutions();
    public Optional<Institution> findInstitution(Long id);
    public void deleteInstitution(Long id);
    public void editInstitution(Long id,Institution institution);
}
