package org.gymanager.model.client;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ClienteUltimosSeguimientosDto extends ClienteDto{

    private List<String> seguimientosEstado;

    public ClienteUltimosSeguimientosDto(ClienteDto clienteDto, List<String> seguimientosEstado) {
        this.setIdCliente(clienteDto.getIdCliente());
        this.setUsuario(clienteDto.getUsuario());
        this.setObjetivo(clienteDto.getObjetivo());
        this.setDireccion(clienteDto.getDireccion());
        this.setFechaNacimiento(clienteDto.getFechaNacimiento());
        this.setObservaciones(clienteDto.getObservaciones());
        this.setClienteEstado(clienteDto.getClienteEstado());
        this.seguimientosEstado = seguimientosEstado;
    }
}
