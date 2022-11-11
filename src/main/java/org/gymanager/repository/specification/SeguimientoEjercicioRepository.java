package org.gymanager.repository.specification;

import org.gymanager.model.domain.SeguimientoEjercicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface SeguimientoEjercicioRepository extends JpaRepository<SeguimientoEjercicio, Long> {

    List<SeguimientoEjercicio> findAllByEjercicioAplicadoRutinaIdRutinaAndFechaCarga(Long idRutina, LocalDate fechaCarga);

    List<SeguimientoEjercicio> findAllByEjercicioAplicadoRutinaIdRutina(Long idRutina);

    List<SeguimientoEjercicio> findAllByEjercicioAplicadoIdEjercicioAplicadoAndFechaCarga(Long idEjercicioAplicado, LocalDate fechaCarga);

    @Query(nativeQuery = true, value = """
            SELECT DISTINCT ea.id_ejercicio \
            FROM {h-schema}seguimiento_ejercicio se \
            JOIN {h-schema}plan p ON p.id_plan = se.id_plan \
            JOIN {h-schema}ejercicio_aplicado ea ON ea.id_ejercicio_aplicado = se.id_ejercicio_aplicado \
            WHERE p.id_cliente = :idCliente""")
    List<Long> findDistinctIdEjercicioByHasSeguimientoWithIdCliente(Long idCliente);

    List<SeguimientoEjercicio> findAllByPlanClienteIdClienteAndEjercicioAplicadoEjercicioIdEjercicio(Long idCliente, Long idEjercicio);
}
