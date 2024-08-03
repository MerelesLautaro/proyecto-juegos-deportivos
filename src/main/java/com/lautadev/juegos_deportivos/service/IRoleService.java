package com.lautadev.juegos_deportivos.service;

import com.lautadev.juegos_deportivos.model.Role;

import java.util.List;
import java.util.Optional;

public interface IRoleService {
    public void saveRole(Role role);
    public List<Role> getRoles();
    public Optional<Role> findRole(Long id);
    public void deleteRole(Long id);
    public void editRole(Long id,Role role);
}
