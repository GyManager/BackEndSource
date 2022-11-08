package org.gymanager.service.implementation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gymanager.model.client.ClientsSummary;
import org.gymanager.model.client.CountClienteEstadoDto;
import org.gymanager.model.client.CountFeedbackFinDiaDto;
import org.gymanager.model.client.EstadoSeguimientoCountDto;
import org.gymanager.model.domain.EstadoSeguimiento;
import org.gymanager.service.specification.ClienteService;
import org.gymanager.service.specification.DashboardService;
import org.gymanager.service.specification.EstadoSeguimientoService;
import org.gymanager.service.specification.SeguimientoService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DashboardServiceImpl implements DashboardService {

    @NonNull
    private ClienteService clienteService;

    @NonNull
    private SeguimientoService seguimientoService;

    @NonNull
    private EstadoSeguimientoService estadoSeguimientoService;

    @Override
    public ClientsSummary getSummary(Long dayCountVencimientoMatricula,
                                     Long dayOverdueVencimientoMatricula,
                                     Long dayCountSinFinalizarDia,
                                     Long dayCountSeguimientoFinDiaEstado,
                                     Long dayCountSeguimientoFinDiaFecha) {

        var cantidadClientesConMatriculaProximoVencimiento = clienteService.getIdClientesConMatriculaProximoVencimiento(
                dayCountVencimientoMatricula,
                dayOverdueVencimientoMatricula
        );

        var countClientesByEstado = clienteService.getCountClientesByClienteEstado().stream()
                .map(CountClienteEstadoDto::new)
                .toList();

        var cantidadClientesSinFinalizarDia = clienteService.getIdClientesSinFinalizarDia(
                dayCountSinFinalizarDia
        );

        var estadoSeguimientoCountList = estadoSeguimientoService.getEstadoSeguimientosEntities()
                .stream()
                .map(estadoSeguimiento -> mapEstadoSeguimientoToCount(estadoSeguimiento, dayCountSeguimientoFinDiaEstado))
                .toList();

        var countByFechaNotOlderThanDays = seguimientoService
                .getCountByFechaNotOlderThanDays(dayCountSeguimientoFinDiaFecha)
                .stream()
                .map(CountFeedbackFinDiaDto::new)
                .toList();

        return new ClientsSummary(
                cantidadClientesConMatriculaProximoVencimiento.size(),
                countClientesByEstado,
                cantidadClientesSinFinalizarDia.size(),
                estadoSeguimientoCountList,
                countByFechaNotOlderThanDays
        );
    }

    private EstadoSeguimientoCountDto mapEstadoSeguimientoToCount(EstadoSeguimiento estadoSeguimiento,
                                                                  Long dayCountSeguimientoFinDiaEstado){
        var idClientesSeguimientoFinDiaByEstado = seguimientoService.getIdClientesCountSeguimientoFinDiaByEstado(
                dayCountSeguimientoFinDiaEstado,
                estadoSeguimiento
        );

        return new EstadoSeguimientoCountDto(estadoSeguimiento, idClientesSeguimientoFinDiaByEstado.size());
    }
}
