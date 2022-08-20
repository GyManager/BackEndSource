package org.gymanager.service.specification;

import org.gymanager.model.client.PlanDto;
import org.gymanager.model.domain.Plan;

import java.util.List;

public interface PlanService {

    List<PlanDto> getPlansByClientId(Long idCliente);

    PlanDto getPlanById(Long idPlan);

    Plan getPlanEntityById(Long idPlan);
}
