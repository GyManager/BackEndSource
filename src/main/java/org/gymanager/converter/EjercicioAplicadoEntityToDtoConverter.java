package org.gymanager.converter;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.gymanager.model.client.EjercicioAplicadoDto;
import org.gymanager.model.domain.EjercicioAplicado;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class EjercicioAplicadoEntityToDtoConverter implements GyManagerConverter<EjercicioAplicado, EjercicioAplicadoDto> {

    @NonNull
    private EjercicioEntityToDtoConverter ejercicioEntityToDtoConverter;

    @Override
    public EjercicioAplicadoDto convert(EjercicioAplicado source) {
        var ejercicioAplicado = new EjercicioAplicadoDto();
        ejercicioAplicado.setIdEjercicioAplicado(source.getIdEjercicioAplicado());
        ejercicioAplicado.setEjercicio(Objects.isNull(source.getEjercicio()) ? null :
                ejercicioEntityToDtoConverter.convert(source.getEjercicio()));
        ejercicioAplicado.setBloque(Objects.isNull(source.getBloque()) ? null :
                source.getBloque().getNombre());
        ejercicioAplicado.setSeries(source.getSeries());
        ejercicioAplicado.setRepeticiones(source.getRepeticiones());
        ejercicioAplicado.setPausaMacro(source.getPausaMacro());
        ejercicioAplicado.setPausaMicro(source.getPausaMicro());
        ejercicioAplicado.setCarga(source.getCarga());
        ejercicioAplicado.setTiempo(source.getTiempo());
        ejercicioAplicado.setEsTemplate(source.getEsTemplate());
        return ejercicioAplicado;
    }
}
