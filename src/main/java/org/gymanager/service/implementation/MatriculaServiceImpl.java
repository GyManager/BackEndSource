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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

import static java.time.LocalDateTime.now;
import static org.gymanager.model.enums.MatriculasFilter.NO_VENCIDAS;

@Service
@RequiredArgsConstructor
@Slf4j
public class MatriculaServiceImpl implements MatriculaService {

    private static final String MATRICULA_SUPERPUESTA_CON_OTRA = """
            La matricula que esta intentando crear se superpone con otra matricula con fecha de inicio (%s)
            y fecha de vencimiento (%s).""";
    private static final String FECHAS_INVALIDAS = """
            La fecha de inicio de la matricula no puede ser posterior a la fecha de vencimiento""";
    private static final String FECHA_VENCIMIENTO_INVALIDA = """
            La fecha de vencimiento de la matricula no puede ser anterior a la fecha de hoy %s""";

    @NonNull
    private MatriculaRepository matriculaRepository;

    @NonNull
    private MatriculaEntityToDtoConverter matriculaEntityToDtoConverter;

    @NonNull
    private ClienteService clienteService;

    @Override
    public List<MatriculaDto> getMatriculasByIdCliente(Long idCliente, MatriculasFilter matriculasFilter) {
        return matriculaEntityToDtoConverter.convert(getMatriculasEntitiesByIdCliente(idCliente, matriculasFilter));
    }

    @Override
    public List<Matricula> getMatriculasEntitiesByIdCliente(Long idCliente, MatriculasFilter matriculasFilter) {
        return switch (matriculasFilter) {
            case TODAS -> matriculaRepository.findAllByClienteIdCliente(idCliente);
            case NO_VENCIDAS -> matriculaRepository.findAllByClienteIdClienteAndFechaVencimientoAfter(idCliente, now());
            case ACTUAL -> matriculaRepository.findAllByClienteIdClienteAndCurrent(idCliente);
        };
    }

    @Override
    @Transactional
    public Long addMatricula(Long idCliente, MatriculaDto matriculaDto) {
        var cliente = clienteService.getClienteEntityById(idCliente);

        matriculaDto.setFechaVencimiento(matriculaDto
                .getFechaInicio()
                .plusMonths(matriculaDto.getCantidadMeses()));

        validarFechaVencimientoNoPasada(matriculaDto.getFechaVencimiento());
        validarFechasNoInvertidas(matriculaDto);
        validarMatriculaNoSeSuperponeConMatriculasExistentes(matriculaDto, idCliente);

        var matricula = new Matricula();
        matricula.setCliente(cliente);
        matricula.setFechaInicio(matriculaDto.getFechaInicio());
        matricula.setFechaPago(matriculaDto.getFechaPago());
        matricula.setFechaVencimiento(matriculaDto.getFechaVencimiento());
        matricula.setCantidadMeses(matriculaDto.getCantidadMeses());
        matricula.setCantidadDiasSemana(matriculaDto.getCantidadDiasSemana());

        return matriculaRepository.save(matricula).getIdMatricula();
    }

    private void validarFechaVencimientoNoPasada(LocalDateTime fechaVencimiento){
        if(fechaVencimiento.isBefore(now())){
            log.error(String.format(FECHA_VENCIMIENTO_INVALIDA, fechaVencimiento.toLocalDate()));
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format(FECHA_VENCIMIENTO_INVALIDA, fechaVencimiento.toLocalDate()));
        }
    }

    private void validarFechasNoInvertidas(MatriculaDto matriculaDto) {
        if(matriculaDto.getFechaInicio().isAfter(matriculaDto.getFechaVencimiento())){
            log.error(FECHAS_INVALIDAS);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, FECHAS_INVALIDAS);
        }
    }

    private void validarMatriculaNoSeSuperponeConMatriculasExistentes(MatriculaDto matriculaDto, Long idCliente) {
        var matriculas = getMatriculasEntitiesByIdCliente(idCliente, NO_VENCIDAS);
        var matriculaSuperpuesta = matriculas.stream()
                .filter(matricula -> matriculasSuperpuestas(matriculaDto, matricula))
                .findFirst();

        if(matriculaSuperpuesta.isPresent()){
            var error = String.format(MATRICULA_SUPERPUESTA_CON_OTRA,
                    matriculaSuperpuesta.get().getFechaInicio().toLocalDate(),
                    matriculaSuperpuesta.get().getFechaVencimiento().toLocalDate());
            log.error(error);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, error);
        }
    }

    private Boolean matriculasSuperpuestas(MatriculaDto matriculaDto, Matricula matricula){
        var inicio = matricula.getFechaInicio();
        var fin = matricula.getFechaVencimiento();
        var inicioNuevo = matriculaDto.getFechaInicio();
        var finNuevo = matriculaDto.getFechaVencimiento();
        return fechaEstaEnPeriodo(inicioNuevo, inicio, fin)
                || fechaEstaEnPeriodo(finNuevo, inicio, fin)
                || fechaEstaEnPeriodo(inicio, inicioNuevo, finNuevo)
                || fechaEstaEnPeriodo(fin, inicioNuevo, finNuevo)
                || inicioNuevo.toLocalDate().isEqual(inicio.toLocalDate());
    }

    private Boolean fechaEstaEnPeriodo(LocalDateTime fecha, LocalDateTime inicioPeriodo, LocalDateTime finPeriodo){
        return fecha.toLocalDate().isAfter(inicioPeriodo.toLocalDate())
                && fecha.toLocalDate().isBefore(finPeriodo.toLocalDate());
    }
}
