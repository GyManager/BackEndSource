package org.gymanager.model.client;

import org.gymanager.model.domain.EstadoSeguimiento;

public record EstadoSeguimientoCountDto(Long idEstadoSeguimiento,
                                        String descripcion,
                                        Long puntaje,
                                        String color,
                                        Integer cantidad){

    public EstadoSeguimientoCountDto(EstadoSeguimiento estadoSeguimiento, Integer cantidad) {
        this(estadoSeguimiento.getIdEstadoSeguimiento(),
                estadoSeguimiento.getDescripcion(),
                estadoSeguimiento.getPuntaje(),
                estadoSeguimiento.getColor(),
                cantidad);
    }
}
