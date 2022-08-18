package org.gymanager.repository.specification;

import org.gymanager.model.domain.Bloque;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BloqueRepository extends JpaRepository<Bloque, Long> {

    Optional<Bloque> findByNombre(String nombre);
}
