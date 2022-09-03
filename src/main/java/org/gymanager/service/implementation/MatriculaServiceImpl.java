package org.gymanager.service.implementation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gymanager.converter.MatriculaEntityToDtoConverter;
import org.gymanager.model.client.MatriculaDto;
import org.gymanager.repository.specification.MatriculaRepository;
import org.gymanager.service.specification.MatriculaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MatriculaServiceImpl implements MatriculaService {

    @NonNull
    private MatriculaRepository matriculaRepository;

    @NonNull
    private MatriculaEntityToDtoConverter matriculaEntityToDtoConverter;

    @Override
    public List<MatriculaDto> getMatriculasByIdCliente(Long idCliente, Boolean last) {
        if(last){
            return matriculaEntityToDtoConverter.convert(matriculaRepository.findAllByClienteIdClienteAndFechaInicioLast(idCliente));
        } else {
            return matriculaEntityToDtoConverter.convert(matriculaRepository.findAllByClienteIdCliente(idCliente));
        }
    }
}
