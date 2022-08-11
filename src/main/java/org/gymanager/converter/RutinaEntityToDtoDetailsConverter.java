package org.gymanager.converter;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.gymanager.model.client.RutinaDtoDetails;
import org.gymanager.model.domain.Rutina;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RutinaEntityToDtoDetailsConverter implements GyManagerConverter<Rutina, RutinaDtoDetails> {

    @NonNull
    private EjercicioAplicadoEntityToDtoConverter ejercicioAplicadoEntityToDtoConverter;

    @Override
    public RutinaDtoDetails convert(Rutina source) {
        var rutinaDtoDetails = new RutinaDtoDetails();
        rutinaDtoDetails.setIdRutina(source.getIdRutina());
        rutinaDtoDetails.setNombre(source.getNombre());
        rutinaDtoDetails.setEsTemplate(source.getEsTemplate());
        rutinaDtoDetails.setEjerciciosAplicados(ejercicioAplicadoEntityToDtoConverter.convert(source.getEjercicioAplicados()));
        return rutinaDtoDetails;
    }
}
