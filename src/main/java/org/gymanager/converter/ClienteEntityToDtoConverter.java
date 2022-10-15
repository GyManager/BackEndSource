package org.gymanager.converter;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.gymanager.model.client.ClienteDto;
import org.gymanager.model.domain.Cliente;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ClienteEntityToDtoConverter implements GyManagerConverter<Cliente, ClienteDto> {

    @NonNull
    private UsuarioEntityToDtoConverter usuarioEntityToDtoConverter;

    @Override
    public ClienteDto convert(Cliente source) {
        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setIdCliente(source.getIdCliente());
        if(Objects.nonNull(source.getUsuario())){
            clienteDto.setUsuario(usuarioEntityToDtoConverter.convert(source.getUsuario()));
        }
        clienteDto.setObjetivo(Objects.isNull(source.getObjetivo()) ? null : source.getObjetivo().getObjetivo());
        clienteDto.setDireccion(source.getDireccion());
        clienteDto.setFechaNacimiento(source.getFechaNacimiento());
        clienteDto.setObservaciones(source.getObservaciones());
        clienteDto.setClienteEstado(source.getClienteEstado().getEstado());
        return clienteDto;
    }
}
