package org.gymanager.converter;

import lombok.RequiredArgsConstructor;
import org.gymanager.model.client.clientes.ClienteDto;
import org.gymanager.model.domain.clientes.Cliente;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ClienteEntityToDtoConverter implements GyManagerConverter<Cliente, ClienteDto> {

    @Override
    public ClienteDto convert(Cliente source) {
        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setIdPersona(source.getIdPersona());
        if(Objects.nonNull(source.getUsuario())){
            clienteDto.setIdUsuario(source.getUsuario().getIdUsuario());
            clienteDto.setMail(source.getUsuario().getMail());
        }
        clienteDto.setNumeroDocumento(source.getNumeroDocumento());
        if(Objects.nonNull(source.getTipoDocumento())){
            clienteDto.setIdTipoDocumento(source.getTipoDocumento().getIdTipoDocumento());
            clienteDto.setTipoDocumento(source.getTipoDocumento().getTipo());
        }
        clienteDto.setNombre(source.getNombre());
        clienteDto.setApellido(source.getApellido());
        clienteDto.setDireccion(source.getDireccion());
        clienteDto.setFechaNacimiento(source.getFechaNacimiento());
        clienteDto.setCelular(source.getCelular());
        clienteDto.setObjetivo(source.getObjetivo());
        return clienteDto;
    }
}
