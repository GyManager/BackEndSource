package org.gymanager.utils;

import org.springframework.security.core.Authentication;

public class UserPermissionValidation {

    private UserPermissionValidation() {}

    public static String getUsername(Authentication authentication){
        return authentication.getName();
    }

    public static boolean userHasPermission(Authentication authentication, Permisos permiso){
        return authentication.getAuthorities()
                .stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(permiso.getName()));
    }
}
