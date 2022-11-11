package org.gymanager.converter;

import org.gymanager.model.client.MedidasClienteSmallDto;
import org.gymanager.model.domain.MedidasCliente;
import org.springframework.stereotype.Component;

@Component
public class MedidasClienteEntityToSmallDtoConverter implements GyManagerConverter<MedidasCliente, MedidasClienteSmallDto> {

    @Override
    public MedidasClienteSmallDto convert(MedidasCliente source) {
        return new MedidasClienteSmallDto(
                source.getIdMedidas(),
                source.getFecha()
        );
    }
}
