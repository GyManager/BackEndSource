package org.gymanager.repository.specification;

import org.gymanager.model.domain.Rutina;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RutinaRepository extends JpaRepository<Rutina, Long> {

    List<Rutina> findByMicroPlanIdMicroPlan(Long idMicroPlan);
}
