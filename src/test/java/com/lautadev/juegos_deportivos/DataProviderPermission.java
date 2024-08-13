package com.lautadev.juegos_deportivos;

import com.lautadev.juegos_deportivos.model.Permission;

import java.util.List;
import java.util.Optional;

public class DataProviderPermission {

    public static List<Permission> permissionsListMock(){
        return List.of(
                new Permission(1L,"CREATE"),
                new Permission(2L,"READ"),
                new Permission(3L,"UPDATE"),
                new Permission(4L,"DELETE")
        );
    }

    public static Permission permissionMock(){
        return new Permission(1L,"CREATE");
    }

    public static Optional<Permission> permissionMockNotFound(){
        return Optional.empty();
    }

    public static Permission newPermissionMock(){
        return new Permission(5L,"UPGRADE");
    }
}
