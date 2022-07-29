package org.gymanager.converter;

import lombok.RequiredArgsConstructor;
import org.gymanager.model.client.EjercicioDto;
import org.gymanager.model.domain.Ejercicio;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class EjercicioEntityToDtoConverter implements GyManagerConverter<Ejercicio, EjercicioDto> {

    @Override
    public EjercicioDto convert(Ejercicio source) {
        var ejercicioDto = new EjercicioDto();
        ejercicioDto.setIdEjercicio(source.getIdEjercicio());
        ejercicioDto.setNombre(source.getNombre());
        ejercicioDto.setTipoEjercicio(Objects.isNull(source.getTipoEjercicio()) ? null :
                source.getTipoEjercicio().getNombre());
        ejercicioDto.setVideo(source.getVideo());
        return ejercicioDto;
    }
}
