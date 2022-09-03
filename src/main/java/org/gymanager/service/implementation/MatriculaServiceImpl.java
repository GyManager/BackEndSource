package org.gymanager.service.implementation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gymanager.converter.MatriculaEntityToDtoConverter;
import org.gymanager.model.client.MatriculaDto;
import org.gymanager.model.domain.Matricula;
import org.gymanager.repository.specification.MatriculaRepository;
import org.gymanager.service.specification.ClienteService;
import org.gymanager.service.specification.MatriculaService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MatriculaServiceImpl implements MatriculaService {

    @NonNull
    private MatriculaRepository matriculaRepository;

    @NonNull
    private MatriculaEntityToDtoConverter matriculaEntityToDtoConverter;

    @NonNull
    private ClienteService clienteService;

    @Override
    public List<MatriculaDto> getMatriculasByIdCliente(Long idCliente, Boolean last) {
        if(last){
            return matriculaEntityToDtoConverter.convert(matriculaRepository.findAllByClienteIdClienteAndFechaInicioLast(idCliente));
        } else {
            return matriculaEntityToDtoConverter.convert(matriculaRepository.findAllByClienteIdCliente(idCliente));
        }
    }

    @Override
    @Transactional
    public Long addMatricula(Long idCliente, MatriculaDto matriculaDto) {
        var cliente = clienteService.getClienteEntityById(idCliente);

        var matricula = new Matricula();
        matricula.setCliente(cliente);
        matricula.setFechaInicio(matriculaDto.getFechaInicio());
        matricula.setFechaPago(matriculaDto.getFechaPago());
        matricula.setFechaVencimiento(matriculaDto.getFechaVencimiento());
        matricula.setCantidadMeses(matriculaDto.getCantidadMeses());
        matricula.setCantidadDiasSemana(matriculaDto.getCantidadDiasSemana());

        return matriculaRepository.save(matricula).getIdMatricula();
    }
}
