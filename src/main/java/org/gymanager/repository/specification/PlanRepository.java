package org.gymanager.repository.specification;

import org.gymanager.model.domain.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PlanRepository extends JpaRepository<Plan, Long> {

    List<Plan> findByClienteIdCliente(Long idCliente);

    List<Plan> findAllByClienteIdClienteAndFechaDesdeAfter(Long idCliente, LocalDate date);

    List<Plan> findAllByClienteIdClienteAndFechaHastaLessThanEqual(Long idCliente, LocalDate date);

    List<Plan> findAllByClienteIdClienteAndFechaHastaGreaterThanAndFechaDesdeLessThanEqual(Long idCliente, LocalDate hasta, LocalDate desde);
}
