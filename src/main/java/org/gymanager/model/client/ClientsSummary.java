package org.gymanager.model.client;

import java.util.List;

public record ClientsSummary(
        Integer cantidadClientesConMatriculaProximoVencimiento,
        List<CountClienteEstadoDto> countClienteEstado,
        Integer cantidadClientesSinFinalizarDia,
        List<EstadoSeguimientoCountDto> estadoSeguimientoCounts) {
}
