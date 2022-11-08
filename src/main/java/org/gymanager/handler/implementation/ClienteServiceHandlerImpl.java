package org.gymanager.handler.implementation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gymanager.handler.specification.ClienteServiceHandler;
import org.gymanager.model.client.ClienteUltimosSeguimientosDto;
import org.gymanager.model.domain.EstadoSeguimiento;
import org.gymanager.model.enums.ClienteSortBy;
import org.gymanager.model.page.GyManagerPage;
import org.gymanager.service.specification.ClienteService;
import org.gymanager.service.specification.EstadoSeguimientoService;
import org.gymanager.service.specification.SeguimientoService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClienteServiceHandlerImpl implements ClienteServiceHandler {

    @NonNull
    private SeguimientoService seguimientoService;

    @NonNull
    private EstadoSeguimientoService estadoSeguimientoService;

    @NonNull
    private ClienteService clienteService;

    @Override
    public GyManagerPage<ClienteUltimosSeguimientosDto> getClientesByUltimosSeguimientos(
            String fuzzySearch, Integer page, Integer pageSize, ClienteSortBy sortBy, Sort.Direction direction,
            Long cantidadDias, List<Long> idEstadoSeguimientoList) {

        var estadoSeguimientosMapIdsClientes = idEstadoSeguimientoList
                .stream()
                .map(estadoSeguimientoService::getEstadoSeguimientoById)
                .collect(Collectors.toMap(
                        Function.identity(),
                        estadoSeguimiento -> seguimientoService.getIdClientesCountSeguimientoFinDiaByEstado(cantidadDias, estadoSeguimiento)
                ));

        var clientes = clienteService.getClientes(
                fuzzySearch,
                page,
                pageSize,
                sortBy,
                direction,
                estadoSeguimientosMapIdsClientes.values().stream().flatMap(List::stream).distinct().toList()
        );

        return clientes.map(clienteDto ->
                new ClienteUltimosSeguimientosDto(
                        clienteDto,
                        estadoSeguimientosMapIdsClientes.entrySet()
                                .stream()
                                .filter(entry -> entry.getValue().contains(clienteDto.getIdCliente()))
                                .map(Map.Entry::getKey)
                                .sorted(Comparator.comparing(EstadoSeguimiento::getPuntaje))
                                .map(EstadoSeguimiento::getDescripcion)
                                .toList()
                    )
        );
    }
}
