package org.gymanager.service.specification;

import org.gymanager.model.client.ClientePlanResumenDto;
import org.gymanager.model.client.PlanDto;
import org.gymanager.model.client.PlanDtoDetails;
import org.gymanager.model.domain.EstadoSeguimiento;
import org.gymanager.model.domain.Plan;
import org.gymanager.model.enums.PlanesFilter;

import java.util.List;

public interface PlanService {

    List<PlanDto> getPlansByIdCliente(Long idCliente, PlanesFilter planesFilter, Boolean validateUser);

    List<Plan> getPlansEntitiesByIdCliente(Long idCliente, PlanesFilter planesFilter);

    PlanDto getPlanById(Long idPlan, Boolean validateUser);

    Plan getPlanEntityById(Long idPlan);

    Long addPlan(Long idCliente, PlanDtoDetails planDtoDetails);

    void updatePlanById(Long idCliente, Long idPlan, PlanDtoDetails planDtoDetails);

    void deletePlanById(Long idCliente, Long idPlan);

    ClientePlanResumenDto getResumenPlanesClienteById(Long idCliente);

    void updatePlanSeguimientoById(Plan plan, String observacion, EstadoSeguimiento estadoSeguimiento);
}
