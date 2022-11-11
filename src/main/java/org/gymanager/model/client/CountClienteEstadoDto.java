package org.gymanager.model.client;

import org.gymanager.model.domain.CountClienteEstado;

import java.math.BigInteger;

public record CountClienteEstadoDto(String clienteEstado, BigInteger cantidad) {
    public CountClienteEstadoDto(CountClienteEstado countClienteEstado){
        this(countClienteEstado.getClienteEstado(), countClienteEstado.getCantidad());
    }
}
