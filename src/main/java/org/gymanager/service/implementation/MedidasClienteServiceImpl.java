package org.gymanager.service.implementation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gymanager.converter.MedidasClienteEntityToDtoConverter;
import org.gymanager.model.client.MedidasClienteDto;
import org.gymanager.model.domain.MedidasCliente;
import org.gymanager.model.enums.MedidasClienteFilter;
import org.gymanager.repository.specification.MedidasClienteRepository;
import org.gymanager.service.specification.ClienteService;
import org.gymanager.service.specification.MedidasClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MedidasClienteServiceImpl implements MedidasClienteService {

    private static final String MEDIDAS_CLIENTE_NO_ENCONTRADO = "Medidas cliente no encontrado";
    private static final String MEDIDAS_NO_CORRESPONDE_CON_CLIENTE = "Las medidas no corresponden con el cliente";

    @NonNull
    private MedidasClienteRepository medidasClienteRepository;

    @NonNull
    private MedidasClienteEntityToDtoConverter medidasClienteEntityToDtoConverter;

    @NonNull
    private ClienteService clienteService;

    @Override
    public List<MedidasClienteDto> getMedidasByIdCliente(Long idCliente, MedidasClienteFilter medidasClienteFilter) {
        return medidasClienteEntityToDtoConverter.convert(switch (medidasClienteFilter){
            case TODAS, LAST -> medidasClienteRepository.findAllByClienteIdCliente(idCliente);
            //TODO: completar caso LAST
        });
    }

    @Override
    public MedidasCliente getMedidasClienteEntityById(Long idMedidas){
        var medidasCliente = medidasClienteRepository.findById(idMedidas);

        if(medidasCliente.isEmpty()){
            log.error(MEDIDAS_CLIENTE_NO_ENCONTRADO);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, MEDIDAS_CLIENTE_NO_ENCONTRADO);
        }

        return medidasCliente.get();
    }

    @Override
    public Long addMedidas(Long idCliente, MedidasClienteDto medidasClienteDto) {
        return null;
    }

    @Override
    public void updateMedidasById(Long idMedidas, MedidasClienteDto medidasClienteDto) {

    }

    @Override
    public void deleteMedidasById(Long idCliente, Long idMedidas) {
        var medidasCliente = getMedidasClienteEntityById(idMedidas);

        if(!medidasCliente.getCliente().getIdCliente().equals(idCliente)){
            log.error(MEDIDAS_NO_CORRESPONDE_CON_CLIENTE);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, MEDIDAS_NO_CORRESPONDE_CON_CLIENTE);
        }

        medidasClienteRepository.delete(medidasCliente);
    }
}
