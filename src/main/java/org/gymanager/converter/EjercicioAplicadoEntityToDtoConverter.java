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

    @Override
    public EjercicioAplicadoDto convert(EjercicioAplicado source) {
        var ejercicioAplicado = new EjercicioAplicadoDto();
        ejercicioAplicado.setIdEjercicioAplicado(source.getIdEjercicioAplicado());
        if(Objects.nonNull(source.getEjercicio())){
            ejercicioAplicado.setIdEjercicio(source.getEjercicio().getIdEjercicio());
            ejercicioAplicado.setNombreEjercicio(source.getEjercicio().getNombre());
            ejercicioAplicado.setTipoEjercicio(Objects.isNull(source.getEjercicio().getTipoEjercicio()) ? null :
                    source.getEjercicio().getTipoEjercicio().getNombre());
        }
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
