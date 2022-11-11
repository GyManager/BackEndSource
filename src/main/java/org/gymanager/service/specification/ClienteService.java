package org.gymanager.service.specification;

import org.gymanager.model.client.ClienteDto;
import org.gymanager.model.domain.Cliente;
import org.gymanager.model.domain.CountClienteEstado;
import org.gymanager.model.enums.ClienteSortBy;
import org.gymanager.model.page.GyManagerPage;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface ClienteService {

    GyManagerPage<ClienteDto> getClientes(String fuzzySearch, Integer page, Integer pageSize, ClienteSortBy sortBy,
                                          Sort.Direction direction, Long matriculaVenceEn, Long matriculaVenceEnOverdue,
                                          Long sinFinalizarRutinaEn);

    GyManagerPage<ClienteDto> getClientes(String fuzzySearch, Integer page, Integer pageSize,
                                          ClienteSortBy sortBy, Sort.Direction direction,
                                          List<Long> idClientes);

    ClienteDto getClientesById(Long idCliente);

    Cliente getClienteEntityById(Long idCliente);

    Long addCliente(ClienteDto clienteDto);

    void updateClienteById(Long idCliente, ClienteDto clienteDto, Boolean validateUser, Boolean reactivate);

    void deleteClienteById(Long idCliente);

    List<Long> getIdClientesConMatriculaProximoVencimiento(Long dayCount, Long dayOverdue);

    List<CountClienteEstado> getCountClientesByClienteEstado();

    List<Long> getIdClientesSinFinalizarDia(Long dayCount);
}
