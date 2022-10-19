package org.gymanager.model.client;

import java.time.LocalDate;

public record SeguimientoFinDiaDtoDetail(
        Long idSeguimientoFinDia,
        Long idRutina,
        LocalDate fechaCarga,
        String observacion,
        EstadoSeguimientoDto estadoSeguimientoDto){
}
