package org.gymanager.repository.specification;

import org.gymanager.model.domain.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RolRepository extends JpaRepository<Rol, Long> {

    List<Rol> findAllByNombreRolIn(List<String> nombresRoles);
}
