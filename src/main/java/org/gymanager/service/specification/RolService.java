package org.gymanager.service.specification;

import org.gymanager.model.domain.Rol;

import java.util.List;

public interface RolService {

    List<String> getRoles();

    List<Rol> getRolEntities();

    List<Rol> getRolEntitiesByRolNames(List<String> roles);
}
