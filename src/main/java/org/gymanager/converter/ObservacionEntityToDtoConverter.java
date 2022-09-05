package org.gymanager.converter;

import lombok.RequiredArgsConstructor;
import org.gymanager.model.client.ObservacionDto;
import org.gymanager.model.domain.Observacion;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ObservacionEntityToDtoConverter implements GyManagerConverter<Observacion, ObservacionDto> {

    @Override
    public ObservacionDto convert(Observacion source) {
        var observacionDto = new ObservacionDto();
        observacionDto.setIdObservacion(source.getIdObservacion());
        observacionDto.setObservacion(source.getObservacion());
        observacionDto.setNumeroSemana(source.getNumeroSemana());
        return observacionDto;
    }
}
