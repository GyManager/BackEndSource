package org.gymanager.converter;

import lombok.RequiredArgsConstructor;
import org.gymanager.model.client.HerramientaDto;
import org.gymanager.model.domain.Herramienta;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HerramientaEntityToDtoConverter implements GyManagerConverter<Herramienta, HerramientaDto> {

    @Override
    public HerramientaDto convert(Herramienta source) {
        var herramientaDto = new HerramientaDto();
        herramientaDto.setIdHerramienta(source.getIdHerramienta());
        herramientaDto.setDescripcion(source.getDescripcion());
        herramientaDto.setNombre(source.getNombre());
        return herramientaDto;
    }
}
