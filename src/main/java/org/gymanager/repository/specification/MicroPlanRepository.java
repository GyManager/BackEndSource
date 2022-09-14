package org.gymanager.repository.specification;

import org.gymanager.model.domain.MicroPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface MicroPlanRepository extends JpaRepository<MicroPlan, Long>, JpaSpecificationExecutor<MicroPlan> {

    List<MicroPlan> findByPlanIdPlan(Long idPlan);

    List<MicroPlan> findByNombreIgnoreCaseAndEsTemplate(String name, Boolean esTemplate);
}
