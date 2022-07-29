package org.gymanager.converter;

import lombok.RequiredArgsConstructor;
import org.gymanager.model.client.PasoDto;
import org.gymanager.model.domain.Paso;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PasoEntityToDtoConverter implements GyManagerConverter<Paso, PasoDto> {

    @Override
    public PasoDto convert(Paso source) {
        var pasoDto = new PasoDto();
        pasoDto.setIdPaso(source.getIdPaso());
        pasoDto.setNumeroPaso(source.getNumeroPaso());
        pasoDto.setContenido(source.getContenido());
        pasoDto.setImagen(source.getImagen());
        return pasoDto;
    }
}
