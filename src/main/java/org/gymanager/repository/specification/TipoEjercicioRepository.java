package org.gymanager.repository.specification;

import org.gymanager.model.domain.TipoEjercicio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TipoEjercicioRepository extends JpaRepository<TipoEjercicio, Long> {
    Optional<TipoEjercicio> findByNombre(String nombre);
}
