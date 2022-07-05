package org.gymanager.service.implementation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gymanager.converter.ClienteEntityToDtoConverter;
import org.gymanager.model.client.clientes.ClienteDto;
import org.gymanager.model.domain.clientes.Cliente;
import org.gymanager.repository.specification.ClienteRepository;
import org.gymanager.service.specification.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClienteServiceImpl implements ClienteService {

    private static final String CLIENTE_NO_ENCONTRADO = "Cliente no encontrado";

    @NonNull
    private ClienteRepository clienteRepository;

    @NonNull
    private ClienteEntityToDtoConverter clienteEntityToDtoConverter;

    @Override
    @Transactional
    public List<ClienteDto> getClientes() {
        return clienteEntityToDtoConverter.convert(clienteRepository.findAll());
    }

    @Override
    @Transactional
    public ClienteDto getClientesById(Long idCliente) {
        return clienteEntityToDtoConverter.convert(buscarClientePorIdYValidarExistencia(idCliente));
    }

    private Cliente buscarClientePorIdYValidarExistencia(Long idCliente){
        Optional<Cliente> cliente = clienteRepository.findById(idCliente);

        if(cliente.isEmpty()){
            log.error(CLIENTE_NO_ENCONTRADO);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, CLIENTE_NO_ENCONTRADO);
        }

        return cliente.get();
    }
}
