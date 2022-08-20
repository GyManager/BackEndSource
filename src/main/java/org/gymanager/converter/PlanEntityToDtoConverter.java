package org.gymanager.converter;

import lombok.RequiredArgsConstructor;
import org.gymanager.model.client.PlanDto;
import org.gymanager.model.domain.Plan;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class PlanEntityToDtoConverter implements GyManagerConverter<Plan, PlanDto> {

    @Override
    public PlanDto convert(Plan source) {
        var planDto = new PlanDto();
        planDto.setIdPlan(source.getIdPlan());
        planDto.setIdCliente(Objects.isNull(source.getCliente()) ? null : source.getCliente().getIdCliente());
        planDto.setIdUsuarioProfesor(Objects.isNull(source.getUsuarioProfesor()) ? null : source.getUsuarioProfesor().getIdUsuario());
        planDto.setObjetivo(Objects.isNull(source.getObjetivo()) ? null : source.getObjetivo().getObjetivo());
        planDto.setFechaDesde(source.getFechaDesde());
        planDto.setFechaHasta(source.getFechaHasta());
        planDto.setDescripcion(source.getDescripcion());
        return planDto;
    }
}
