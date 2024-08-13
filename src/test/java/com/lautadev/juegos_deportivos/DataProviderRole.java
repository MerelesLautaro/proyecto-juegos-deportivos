package com.lautadev.juegos_deportivos;

import com.lautadev.juegos_deportivos.model.Permission;
import com.lautadev.juegos_deportivos.model.Role;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class DataProviderRole {

    public static List<Role> roleListMock() {
        Permission createPermission = new Permission(1L, "CREATE");
        Permission readPermission = new Permission(2L, "READ");

        Role adminRole = new Role(1L, "ADMIN", new HashSet<>(Set.of(createPermission, readPermission)));
        Role userRole = new Role(2L, "USER", new HashSet<>(Set.of(readPermission)));

        return List.of(adminRole, userRole);
    }

    public static Role roleMock() {
        Permission createPermission = new Permission(1L, "CREATE");
        Permission readPermission = new Permission(2L, "READ");

        return new Role(1L, "ADMIN", new HashSet<>(Set.of(createPermission, readPermission)));
    }

    public static Optional<Role> roleMockOptional() {
        return Optional.of(roleMock());
    }

    public static Optional<Role> roleMockOptionalNotFound() {
        return Optional.empty();
    }

    public static Role newRoleMock() {
        Permission upgradePermission = new Permission(5L, "UPGRADE");

        return new Role(4L, "MODERATOR", new HashSet<>(Set.of(upgradePermission)));
    }
}
