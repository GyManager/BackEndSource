package org.gymanager.repository.specification;

import org.gymanager.model.domain.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PlanRepository extends JpaRepository<Plan, Long> {

    List<Plan> findByClienteIdClienteAndFechaEliminadoNull(Long idCliente);

    List<Plan> findAllByClienteIdClienteAndFechaDesdeAfterAndFechaEliminadoNull(Long idCliente, LocalDateTime date);

    List<Plan> findAllByClienteIdClienteAndFechaHastaLessThanEqualAndFechaEliminadoNull(Long idCliente, LocalDateTime date);

    List<Plan> findAllByClienteIdClienteAndFechaHastaGreaterThanAndFechaDesdeLessThanEqualAndFechaEliminadoNull(Long idCliente, LocalDateTime hasta, LocalDateTime desde);

    List<Plan> findAllByClienteIdClienteAndFechaEliminadoNotNull(Long idCliente);
}
