package org.gymanager.repository.specification;

import org.gymanager.model.domain.Ejercicio;
import org.gymanager.model.domain.TipoEjercicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface EjercicioRepository extends JpaRepository<Ejercicio, Long>, JpaSpecificationExecutor<Ejercicio> {

    Optional<Ejercicio> findByTipoEjercicioAndNombre(TipoEjercicio tipoEjercicio, String nombre);
}
