package org.gymanager.model.client;

import java.util.List;

public record ClientsSummary(
        Integer cantidadClientesConMatriculaProximoVencimiento,
        List<CountClienteEstadoDto> countClienteEstado) {
}
