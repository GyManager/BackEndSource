package org.gymanager.model.client;

import javax.validation.constraints.NotNull;

public record SeguimientoFinDiaDto(
        String observacion,
        @NotNull(message = "Error falta el estado seguimiento")
        Long idEstadoSeguimiento){
}
