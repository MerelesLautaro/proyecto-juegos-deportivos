package com.lautadev.juegos_deportivos.service;

import com.lautadev.juegos_deportivos.model.Permission;
import com.lautadev.juegos_deportivos.model.Role;
import com.lautadev.juegos_deportivos.repository.IRoleRepository;
import com.lautadev.juegos_deportivos.util.NullAwareBeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class RoleService implements IRoleService{
    @Autowired
    private IRoleRepository roleRepository;

    @Autowired
    private IPermissionService permissionService;

    @Override
    public Role saveRole(Role role) {
        Set<Permission> permissionsList = new HashSet<>();

        for(Permission permission: role.getPermissionSet() ){
            Permission readPermission = permissionService.findPermission(permission.getId()).orElse(null);
            if(readPermission!=null){
                permissionsList.add(readPermission);
            }
        }

        if(!permissionsList.isEmpty())
        {
            role.setPermissionSet(permissionsList);
            return roleRepository.save(role);
        }

        return role;
    }

    @Override
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Optional<Role> findRole(Long id) {
        return roleRepository.findById(id);
    }

    @Override
    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }

    @Override
    public Role editRole(Long id,Role role) {
        Role roleEdit = this.findRole(id).orElse(null);

        NullAwareBeanUtils.copyNonNullProperties(role,roleEdit);

        assert roleEdit != null;
        return this.saveRole(roleEdit);
    }
}
