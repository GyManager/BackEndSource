package org.gymanager.converter;

import lombok.RequiredArgsConstructor;
import org.gymanager.model.client.EstadoSeguimientoDto;
import org.gymanager.model.domain.EstadoSeguimiento;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EstadoSeguimientoEntityToDtoConverter implements GyManagerConverter<EstadoSeguimiento, EstadoSeguimientoDto> {

    @Override
    public EstadoSeguimientoDto convert(EstadoSeguimiento source) {
        return new EstadoSeguimientoDto(
                source.getIdEstadoSeguimiento(),
                source.getDescripcion(),
                source.getPuntaje(),
                source.getColor()
        );
    }
}
