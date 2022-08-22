package org.gymanager.converter;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.gymanager.model.client.PlanDtoDetails;
import org.gymanager.model.domain.Plan;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class PlanEntityToDtoDetailsConverter implements GyManagerConverter<Plan, PlanDtoDetails> {

    @NonNull
    private MicroPlanEntityToDtoDetailsConverter microPlanEntityToDtoDetailsConverter;

    @Override
    public PlanDtoDetails convert(Plan source) {
        var planDtoDetails = new PlanDtoDetails();
        planDtoDetails.setIdPlan(source.getIdPlan());
        planDtoDetails.setIdCliente(Objects.isNull(source.getCliente()) ? null : source.getCliente().getIdCliente());
        planDtoDetails.setIdUsuarioProfesor(Objects.isNull(source.getUsuarioProfesor()) ? null : source.getUsuarioProfesor().getIdUsuario());
        planDtoDetails.setObjetivo(Objects.isNull(source.getObjetivo()) ? null : source.getObjetivo().getObjetivo());
        planDtoDetails.setFechaDesde(source.getFechaDesde());
        planDtoDetails.setFechaHasta(source.getFechaHasta());
        planDtoDetails.setDescripcion(source.getDescripcion());
        planDtoDetails.setDescripcion(source.getDescripcion());
        planDtoDetails.setMicroPlans(Objects.isNull(source.getMicroPlans()) ? null :
                microPlanEntityToDtoDetailsConverter.convert(source.getMicroPlans()));
        return planDtoDetails;
    }
}
