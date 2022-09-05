package org.gymanager.repository.specification;

import org.gymanager.model.domain.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlanRepository extends JpaRepository<Plan, Long> {

    List<Plan> findByClienteIdCliente(Long idCliente);
}
