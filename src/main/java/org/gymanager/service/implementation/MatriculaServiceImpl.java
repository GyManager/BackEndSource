package org.gymanager.service.implementation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gymanager.converter.MatriculaEntityToDtoConverter;
import org.gymanager.model.client.MatriculaDto;
import org.gymanager.model.domain.Matricula;
import org.gymanager.model.enums.MatriculasFilter;
import org.gymanager.repository.specification.MatriculaRepository;
import org.gymanager.service.specification.ClienteService;
import org.gymanager.service.specification.MatriculaService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Date;
import java.util.List;

import static java.time.LocalTime.now;
import static org.gymanager.model.enums.MatriculasFilter.NO_VENCIDAS;

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
    public List<MatriculaDto> getMatriculasByIdCliente(Long idCliente, MatriculasFilter matriculasFilter) {

        return matriculaEntityToDtoConverter.convert(
                switch (matriculasFilter) {
                    case TODAS -> matriculaRepository.findAllByClienteIdCliente(idCliente);
                    case NO_VENCIDAS -> matriculaRepository.findAllByClienteIdClienteAndFechaVencimientoAfter(idCliente, Date.from(Instant.now()));
                    case ACTUAL -> matriculaRepository.findAllByClienteIdClienteAndCurrent(idCliente);
                });
    }

    @Override
    @Transactional
    public Long addMatricula(Long idCliente, MatriculaDto matriculaDto) {
        var cliente = clienteService.getClienteEntityById(idCliente);
        var matriculasExistentes = getMatriculasByIdCliente(idCliente, NO_VENCIDAS);

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
