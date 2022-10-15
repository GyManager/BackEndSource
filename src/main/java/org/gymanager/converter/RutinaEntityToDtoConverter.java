package org.gymanager.converter;

import lombok.RequiredArgsConstructor;
import org.gymanager.model.client.RutinaDto;
import org.gymanager.model.domain.Rutina;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RutinaEntityToDtoConverter implements GyManagerConverter<Rutina, RutinaDto> {

    @Override
    public RutinaDto convert(Rutina source) {
        var rutinaDto = new RutinaDto();
        rutinaDto.setIdRutina(source.getIdRutina());
        rutinaDto.setNombre(source.getNombre());
        rutinaDto.setEsTemplate(source.getEsTemplate());
        return rutinaDto;
    }
}
