package org.gymanager.model.client;

import javax.validation.constraints.NotNull;

public record SeguimientoPlanDto(
        String observacion,
        @NotNull(message = "Error falta el estado seguimiento")
        Long idEstadoSeguimiento){
}
