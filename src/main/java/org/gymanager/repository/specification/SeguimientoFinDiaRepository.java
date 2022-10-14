package org.gymanager.repository.specification;

import org.gymanager.model.domain.SeguimientoFinDia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface SeguimientoFinDiaRepository extends JpaRepository<SeguimientoFinDia, Long> {
    List<SeguimientoFinDia> findAllByRutinaMicroPlanIdMicroPlanAndFechaCarga(Long idMicroPlan, LocalDate fechaCarga);

    List<SeguimientoFinDia> findAllByRutinaMicroPlanIdMicroPlan(Long idMicroPlan);

    List<SeguimientoFinDia> findAllByRutinaMicroPlanIdMicroPlanAndFechaCargaGreaterThanEqualAndFechaCargaLessThan(
            Long idMicroPlan,
            LocalDate beginDate,
            LocalDate endDate);
}
