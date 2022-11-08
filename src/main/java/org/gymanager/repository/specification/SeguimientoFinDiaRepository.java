package org.gymanager.repository.specification;

import org.gymanager.model.domain.CountFeedbackFinDia;
import org.gymanager.model.domain.SeguimientoFinDia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface SeguimientoFinDiaRepository extends JpaRepository<SeguimientoFinDia, Long> {
    List<SeguimientoFinDia> findAllByRutinaMicroPlanIdMicroPlanAndFechaCarga(Long idMicroPlan, LocalDate fechaCarga);

    List<SeguimientoFinDia> findAllByRutinaMicroPlanIdMicroPlan(Long idMicroPlan);

    List<SeguimientoFinDia> findAllByRutinaMicroPlanIdMicroPlanAndFechaCargaGreaterThanEqualAndFechaCargaLessThan(
            Long idMicroPlan,
            LocalDate beginDate,
            LocalDate endDate);

    @Query(nativeQuery = true, value = """
            select p.id_cliente \
            from {h-schema}seguimiento_fin_dia sfd \
            join {h-schema}rutina r on r.id_rutina = sfd.id_rutina \
            join {h-schema}micro_plan mp on mp.id_micro_plan = r.id_micro_plan \
            join {h-schema}plan p on p.id_plan = mp.id_plan \
            where current_date - sfd.fecha_carga <= :dayCount \
            and sfd.id_estado_seguimiento = :idEstadoSeguimiento""")
    List<Long> findCountByIdEstadoSeguimientoAndFechaNotOlderThanDays(Long dayCount, Long idEstadoSeguimiento);

    @Query(value = """
            select cffd from CountFeedbackFinDia cffd
            where current_date - cffd.fechaCarga <= :dayCount""")
    List<CountFeedbackFinDia> findCountByFechaNotOlderThanDays(Double dayCount);
}
