package org.gymanager.converter;

import lombok.RequiredArgsConstructor;
import org.gymanager.model.client.MatriculaDto;
import org.gymanager.model.domain.Matricula;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class MatriculaEntityToDtoConverter implements GyManagerConverter<Matricula, MatriculaDto> {

    @Override
    public MatriculaDto convert(Matricula source) {
        var matriculaDto = new MatriculaDto();
        matriculaDto.setIdMatricula(source.getIdMatricula());
        matriculaDto.setIdCliente(Objects.isNull(source.getCliente()) ? null : source.getCliente().getIdCliente());;
        matriculaDto.setFechaPago(source.getFechaPago());
        matriculaDto.setFechaInicio(source.getFechaInicio());
        matriculaDto.setFechaVencimiento(source.getFechaVencimiento());
        matriculaDto.setCantidadMeses(source.getCantidadMeses());
        matriculaDto.setCantidadDiasSemana(source.getCantidadDiasSemana());
        matriculaDto.setMatriculaEstado(source.getMatriculaEstado().toString());
        return matriculaDto;
    }
}
