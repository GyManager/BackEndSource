package org.gymanager.repository.specification;

import org.gymanager.model.domain.MicroPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MicroPlanRepository extends JpaRepository<MicroPlan, Long>, JpaSpecificationExecutor<MicroPlan> {

}
