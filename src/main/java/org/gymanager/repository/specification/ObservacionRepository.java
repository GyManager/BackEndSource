package org.gymanager.repository.specification;

import org.gymanager.model.domain.Observacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ObservacionRepository extends JpaRepository<Observacion, Long> {

    List<Observacion> findByMicroPlanIdMicroPlan(Long idMicroPlan);
}
