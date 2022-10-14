package org.gymanager.converter;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.gymanager.model.client.SeguimientoEjercicioDto;
import org.gymanager.model.client.SeguimientoFinDiaDtoDetail;
import org.gymanager.model.domain.SeguimientoEjercicio;
import org.gymanager.model.domain.SeguimientoFinDia;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class SeguimientoFinDiaEntityToDtoConverter implements GyManagerConverter<SeguimientoFinDia, SeguimientoFinDiaDtoDetail> {

    @NonNull
    private EstadoSeguimientoEntityToDtoConverter estadoSeguimientoEntityToDtoConverter;

    @Override
    public SeguimientoFinDiaDtoDetail convert(SeguimientoFinDia source) {
        return new SeguimientoFinDiaDtoDetail(
                source.getIdSeguimientoFinDia(),
                Objects.isNull(source.getRutina()) ? null : source.getRutina().getIdRutina(),
                source.getFechaCarga(),
                source.getObservacion(),
                Objects.isNull(source.getEstadoSeguimiento()) ? null :
                        estadoSeguimientoEntityToDtoConverter.convert(source.getEstadoSeguimiento())
        );
    }
}
