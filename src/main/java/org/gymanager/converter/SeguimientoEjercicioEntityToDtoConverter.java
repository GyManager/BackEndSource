package org.gymanager.converter;

import lombok.RequiredArgsConstructor;
import org.gymanager.model.client.SeguimientoEjercicioDto;
import org.gymanager.model.domain.SeguimientoEjercicio;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class SeguimientoEjercicioEntityToDtoConverter implements GyManagerConverter<SeguimientoEjercicio, SeguimientoEjercicioDto> {

    @Override
    public SeguimientoEjercicioDto convert(SeguimientoEjercicio source) {
        return new SeguimientoEjercicioDto(
                source.getIdSeguimientoEjercicio(),
                source.getFechaCarga(),
                Objects.isNull(source.getPlan()) ? null : source.getPlan().getIdPlan(),
                Objects.isNull(source.getEjercicioAplicado()) ? null :
                        source.getEjercicioAplicado().getIdEjercicioAplicado(),
                source.getCargaReal(),
                source.getTiempoReal(),
                source.getObservacion()
        );
    }
}
