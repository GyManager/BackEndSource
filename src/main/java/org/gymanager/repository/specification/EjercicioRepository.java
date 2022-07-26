package org.gymanager.repository.specification;

import org.gymanager.model.domain.Ejercicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EjercicioRepository extends JpaRepository<Ejercicio, Long>, JpaSpecificationExecutor<Ejercicio> {

}
