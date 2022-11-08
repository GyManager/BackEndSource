package org.gymanager.service.implementation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gymanager.model.client.ClientsSummary;
import org.gymanager.model.client.CountClienteEstadoDto;
import org.gymanager.service.specification.ClienteService;
import org.gymanager.service.specification.DashboardService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DashboardServiceImpl implements DashboardService {

    @NonNull
    private ClienteService clienteService;

    @Override
    public ClientsSummary getSummary(Long dayCountVencimientoMatricula,
                                     Long dayOverdueVencimientoMatricula,
                                     Long dayCountSinFinalizarDia) {
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

        return new ClientsSummary(
                cantidadClientesConMatriculaProximoVencimiento.size(),
                countClientesByEstado,
                cantidadClientesSinFinalizarDia.size()
        );
    }
}
