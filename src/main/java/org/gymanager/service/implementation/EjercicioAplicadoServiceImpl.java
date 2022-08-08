package org.gymanager.service.implementation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gymanager.converter.EjercicioAplicadoEntityToDtoConverter;
import org.gymanager.model.client.EjercicioAplicadoDto;
import org.gymanager.model.domain.EjercicioAplicado;
import org.gymanager.model.domain.Rutina;
import org.gymanager.repository.specification.EjercicioAplicadoRepository;
import org.gymanager.service.specification.BloqueService;
import org.gymanager.service.specification.EjercicioAplicadoService;
import org.gymanager.service.specification.EjercicioService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EjercicioAplicadoServiceImpl implements EjercicioAplicadoService {

    @NonNull
    private EjercicioAplicadoRepository ejercicioAplicadoRepository;

    @NonNull
    private EjercicioAplicadoEntityToDtoConverter ejercicioAplicadoEntityToDtoConverter;

    @NonNull
    private BloqueService bloqueService;

    @NonNull
    private EjercicioService ejercicioService;

    @Override
    @Transactional
    public List<EjercicioAplicadoDto> getEjerciciosAplicadosByIdRutina(Long idMicroPlan, Long idRutina) {
        return ejercicioAplicadoEntityToDtoConverter.convert(ejercicioAplicadoRepository.findByRutinaIdRutina(idRutina));
    }

    @Override
    public List<EjercicioAplicado> crearEjerciciosAplicados(List<EjercicioAplicadoDto> ejerciciosAplicados) {
        return ejerciciosAplicados.stream().map(this::crearEjercicioAplicado).collect(Collectors.toList());
    }

    @Override
    public void actualizarEjerciciosAplicadosRutina(List<EjercicioAplicadoDto> ejerciciosAplicadosDtos, Rutina rutina) {

        final var mapEjercicioActExistenteIdEjercicioActActualizadaDto = ejerciciosAplicadosDtos.stream()
                .filter(ejercicioAplicadoDto -> Objects.nonNull(ejercicioAplicadoDto.getIdEjercicioAplicado()))
                .collect(Collectors.toMap(EjercicioAplicadoDto::getIdEjercicioAplicado, Function.identity()));

        rutina.getEjercicioAplicados()
                .removeIf(ejercicioAplicado -> !mapEjercicioActExistenteIdEjercicioActActualizadaDto
                        .containsKey(ejercicioAplicado.getIdEjercicioAplicado()));

        rutina.getEjercicioAplicados().forEach(ejercicioAplicado -> {
            var ejercicioAplicadoDtoActualizado = mapEjercicioActExistenteIdEjercicioActActualizadaDto.get(ejercicioAplicado.getIdEjercicioAplicado());

            var bloque = bloqueService.getBloqueByNombre(ejercicioAplicadoDtoActualizado.getBloque());
            var ejercicio = ejercicioService.getEjercicioEntityById(ejercicioAplicadoDtoActualizado.getIdEjercicio());

            ejercicioAplicado.setEjercicio(ejercicio);
            ejercicioAplicado.setBloque(bloque);
            ejercicioAplicado.setSeries(ejercicioAplicadoDtoActualizado.getSeries());
            ejercicioAplicado.setRepeticiones(ejercicioAplicadoDtoActualizado.getRepeticiones());
            ejercicioAplicado.setPausaMicro(ejercicioAplicadoDtoActualizado.getPausaMicro());
            ejercicioAplicado.setPausaMacro(ejercicioAplicadoDtoActualizado.getPausaMacro());
            ejercicioAplicado.setCarga(ejercicioAplicadoDtoActualizado.getCarga());
            ejercicioAplicado.setTiempo(ejercicioAplicadoDtoActualizado.getTiempo());
            ejercicioAplicado.setEsTemplate(Boolean.TRUE.equals(ejercicioAplicadoDtoActualizado.getEsTemplate()));

        });

        rutina.addAllEjercicioAplicados(crearEjerciciosAplicados(getEjerciciosAplicadosSinId(ejerciciosAplicadosDtos)));
    }

    private List<EjercicioAplicadoDto> getEjerciciosAplicadosSinId(List<EjercicioAplicadoDto> ejercicioAplicadoDtos){
        return ejercicioAplicadoDtos.stream()
                .filter(ejercicioAplicadoDto -> Objects.isNull(ejercicioAplicadoDto.getIdEjercicioAplicado()))
                .toList();
    }

    private EjercicioAplicado crearEjercicioAplicado(EjercicioAplicadoDto ejercicioAplicadoDto){
        var ejercicioAplicado = new EjercicioAplicado();

        var bloque = bloqueService.getBloqueByNombre(ejercicioAplicadoDto.getBloque());
        var ejercicio = ejercicioService.getEjercicioEntityById(ejercicioAplicadoDto.getIdEjercicio());

        ejercicioAplicado.setEjercicio(ejercicio);
        ejercicioAplicado.setBloque(bloque);
        ejercicioAplicado.setSeries(ejercicioAplicadoDto.getSeries());
        ejercicioAplicado.setRepeticiones(ejercicioAplicadoDto.getRepeticiones());
        ejercicioAplicado.setPausaMicro(ejercicioAplicadoDto.getPausaMicro());
        ejercicioAplicado.setPausaMacro(ejercicioAplicadoDto.getPausaMacro());
        ejercicioAplicado.setCarga(ejercicioAplicadoDto.getCarga());
        ejercicioAplicado.setTiempo(ejercicioAplicadoDto.getTiempo());
        ejercicioAplicado.setEsTemplate(Boolean.TRUE.equals(ejercicioAplicadoDto.getEsTemplate()));

        return ejercicioAplicado;
    }
}
