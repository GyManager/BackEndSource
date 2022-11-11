package org.gymanager.converter;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.gymanager.model.client.PlanDto;
import org.gymanager.model.domain.Plan;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class PlanEntityToDtoConverter implements GyManagerConverter<Plan, PlanDto> {

    @NonNull
    private EstadoSeguimientoEntityToDtoConverter estadoSeguimientoEntityToDtoConverter;

    @Override
    public PlanDto convert(Plan source) {
        var planDto = new PlanDto();
        planDto.setIdPlan(source.getIdPlan());
        planDto.setIdCliente(Objects.isNull(source.getCliente()) ? null : source.getCliente().getIdCliente());
        planDto.setIdUsuarioProfesor(Objects.isNull(source.getUsuarioProfesor()) ? null : source.getUsuarioProfesor().getIdUsuario());
        planDto.setUsuarioProfesor(Objects.isNull(source.getUsuarioProfesor()) ? null : source.getUsuarioProfesor().getMail());
        planDto.setObjetivo(Objects.isNull(source.getObjetivo()) ? null : source.getObjetivo().getObjetivo());
        planDto.setFechaDesde(source.getFechaDesde());
        planDto.setFechaHasta(source.getFechaHasta());
        planDto.setDescripcion(source.getDescripcion());
        planDto.setObservacionCliente(source.getObservacionCliente());
        planDto.setEstadoSeguimientoDto(Objects.isNull(source.getEstadoSeguimiento()) ? null :
                estadoSeguimientoEntityToDtoConverter.convert(source.getEstadoSeguimiento()));
        return planDto;
    }
}
