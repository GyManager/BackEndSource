package org.gymanager.service.implementation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gymanager.converter.ClienteEntityToDtoConverter;
import org.gymanager.model.client.ClienteDto;
import org.gymanager.model.domain.Cliente;
import org.gymanager.model.domain.Objetivo;
import org.gymanager.model.domain.Usuario;
import org.gymanager.model.enums.ClienteSortBy;
import org.gymanager.model.page.GyManagerPage;
import org.gymanager.repository.filters.ClienteSpecification;
import org.gymanager.repository.specification.ClienteRepository;
import org.gymanager.service.specification.ClienteService;
import org.gymanager.service.specification.ObjetivoService;
import org.gymanager.service.specification.UsuarioService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Optional;

import static org.apache.commons.lang3.BooleanUtils.isTrue;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClienteServiceImpl implements ClienteService {

    private static final String CLIENTE_NO_ENCONTRADO = "Cliente no encontrado";
    private static final String ROL_CLIENTE = "CLIENTE";

    @Value("${logical-delete}")
    private Boolean logicalDelete;

    @NonNull
    private ClienteRepository clienteRepository;

    @NonNull
    private ClienteEntityToDtoConverter clienteEntityToDtoConverter;

    @NonNull
    private UsuarioService usuarioService;

    @NonNull
    private ObjetivoService objetivoService;

    @Override
    @Transactional
    public GyManagerPage<ClienteDto> getClientes(String fuzzySearch, Integer page, Integer pageSize,
                                                 ClienteSortBy sortBy, Sort.Direction direction) {
        var clienteSpecification = new ClienteSpecification();
        clienteSpecification.setFuzzySearch(fuzzySearch);

        Sort sort = sortBy.equals(ClienteSortBy.NONE) ? Sort.unsorted() : Sort.by(direction, sortBy.getField());
        PageRequest pageable = PageRequest.of(page, pageSize, sort);

        return new GyManagerPage<>(clienteRepository.findAll(clienteSpecification, pageable)
                .map(clienteEntityToDtoConverter::convert));
    }

    @Override
    @Transactional
    public ClienteDto getClientesById(Long idCliente) {
        return clienteEntityToDtoConverter.convert(getClienteEntityById(idCliente));
    }

    @Override
    public Cliente getClienteEntityById(Long idCliente){
        Optional<Cliente> cliente = clienteRepository.findById(idCliente);

        if(cliente.isEmpty()){
            log.error(CLIENTE_NO_ENCONTRADO);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, CLIENTE_NO_ENCONTRADO);
        }

        return cliente.get();
    }

    @Override
    @Transactional
    public Long addCliente(ClienteDto clienteDto) {
        Cliente cliente = new Cliente();

        Long idUsuario = usuarioService.addUsuario(clienteDto.getUsuario(), Collections.singletonList(ROL_CLIENTE));
        Usuario usuario = usuarioService.getUsuarioEntityById(idUsuario);
        Objetivo objetivo = objetivoService.getObjetivoByObjetivo(clienteDto.getObjetivo());

        cliente.setUsuario(usuario);
        cliente.setObjetivo(objetivo);
        cliente.setDireccion(clienteDto.getDireccion());
        cliente.setFechaNacimiento(clienteDto.getFechaNacimiento());
        cliente.setObservaciones(clienteDto.getObservaciones());

        return clienteRepository.save(cliente).getIdCliente();
    }

    @Override
    @Transactional
    public void updateClienteById(Long idCliente, ClienteDto clienteDto) {
        Cliente cliente = getClienteEntityById(idCliente);

        Long idUsuario = cliente.getUsuario().getIdUsuario();
        usuarioService.updateUsuarioById(idUsuario, clienteDto.getUsuario(), Collections.emptyList(), Boolean.FALSE);
        Usuario usuario = usuarioService.getUsuarioEntityById(idUsuario);
        Objetivo objetivo = objetivoService.getObjetivoByObjetivo(clienteDto.getObjetivo());

        cliente.setUsuario(usuario);
        cliente.setObjetivo(objetivo);
        cliente.setDireccion(clienteDto.getDireccion());
        cliente.setFechaNacimiento(clienteDto.getFechaNacimiento());
        cliente.setObservaciones(clienteDto.getObservaciones());

        clienteRepository.save(cliente);
    }

    @Override
    @Transactional
    public void deleteClienteById(Long idCliente) {
        Cliente cliente = getClienteEntityById(idCliente);

        if(isTrue(logicalDelete)){
            usuarioService.removeRolUsuarioById(cliente.getUsuario().getIdUsuario(),
                    Collections.singletonList(ROL_CLIENTE));
        } else {
            clienteRepository.delete(cliente);
            usuarioService.deleteUsuarioById(cliente.getUsuario().getIdUsuario());
        }
    }
}
