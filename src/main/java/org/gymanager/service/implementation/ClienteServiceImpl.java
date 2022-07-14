package org.gymanager.service.implementation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gymanager.model.domain.Cliente;
import org.gymanager.model.enums.ClienteSortBy;
import org.gymanager.model.page.GyManagerPage;
import org.gymanager.repository.filters.ClienteSpecification;
import org.gymanager.repository.specification.ClienteRepository;
import org.gymanager.service.specification.ClienteService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClienteServiceImpl implements ClienteService {

    private static final String CLIENTE_NO_ENCONTRADO = "Cliente no encontrado";

    @NonNull
    private ClienteRepository clienteRepository;

    @Override
    @Transactional
    public GyManagerPage<Cliente> getClientes(String fuzzySearch, Integer page, Integer pageSize,
                                                 ClienteSortBy sortBy, Sort.Direction direction) {
        var clienteSpecification = new ClienteSpecification();
        clienteSpecification.setFuzzySearch(fuzzySearch);

        Sort sort = sortBy.equals(ClienteSortBy.NONE) ? Sort.unsorted() : Sort.by(direction, sortBy.getField());
        PageRequest pageable = PageRequest.of(page, pageSize, sort);

        return new GyManagerPage<>(clienteRepository.findAll(clienteSpecification, pageable));
    }

    @Override
    @Transactional
    public Cliente getClientesById(Long idCliente) {
        Optional<Cliente> cliente = clienteRepository.findById(idCliente);

        if(cliente.isEmpty()){
            log.error(CLIENTE_NO_ENCONTRADO);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, CLIENTE_NO_ENCONTRADO);
        }

        return cliente.get();
    }
}
