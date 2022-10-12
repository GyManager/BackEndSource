package org.gymanager.repository.specification;

import org.gymanager.model.domain.SeguimientoEjercicio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface SeguimientoEjercicioRepository extends JpaRepository<SeguimientoEjercicio, Long> {

    List<SeguimientoEjercicio> findAllByEjercicioAplicadoRutinaIdRutinaAndFechaCarga(Long idRutina, LocalDate fechaCarga);

    List<SeguimientoEjercicio> findAllByEjercicioAplicadoRutinaIdRutina(Long idRutina);
}
