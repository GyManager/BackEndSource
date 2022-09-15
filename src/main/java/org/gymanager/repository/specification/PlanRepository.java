package org.gymanager.repository.specification;

import org.gymanager.model.domain.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PlanRepository extends JpaRepository<Plan, Long> {

    List<Plan> findByClienteIdCliente(Long idCliente);

    List<Plan> findAllByClienteIdClienteAndFechaDesdeAfter(Long idCliente, LocalDateTime date);

    List<Plan> findAllByClienteIdClienteAndFechaHastaLessThanEqual(Long idCliente, LocalDateTime date);

    List<Plan> findAllByClienteIdClienteAndFechaHastaGreaterThanAndFechaDesdeLessThanEqual(Long idCliente, LocalDateTime hasta, LocalDateTime desde);
}
