package org.gymanager.converter;

import org.gymanager.model.client.MedidasClienteDto;
import org.gymanager.model.domain.MedidasCliente;
import org.springframework.stereotype.Component;

@Component
public class MedidasClienteEntityToDtoConverter implements GyManagerConverter<MedidasCliente, MedidasClienteDto> {

    @Override
    public MedidasClienteDto convert(MedidasCliente source) {
        return new MedidasClienteDto(
                source.getIdMedidas(),
                source.getFecha(),
                source.getPeso(),
                source.getAltura(),
                source.getCervical(),
                source.getDorsal(),
                source.getLumbar(),
                source.getCoxalPelvica(),
                source.getCadera(),
                source.getMuslosIzq(),
                source.getMuslosDer(),
                source.getRodillasIzq(),
                source.getRodillasDer(),
                source.getGemelosIzq(),
                source.getGemelosDer(),
                source.getBrazoIzq(),
                source.getBrazoDer(),
                source.getFoto()
        );
    }
}
