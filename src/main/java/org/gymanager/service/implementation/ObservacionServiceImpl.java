package org.gymanager.service.implementation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gymanager.converter.ObservacionEntityToDtoConverter;
import org.gymanager.model.client.ObservacionDto;
import org.gymanager.model.domain.MicroPlan;
import org.gymanager.model.domain.Observacion;
import org.gymanager.repository.specification.ObservacionRepository;
import org.gymanager.service.specification.ObservacionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ObservacionServiceImpl implements ObservacionService {

    @NonNull
    private ObservacionRepository observacionRepository;

    @NonNull
    private ObservacionEntityToDtoConverter observacionEntityToDtoConverter;

    @Override
    public List<ObservacionDto> getObservacionesByIdMicroPlan(Long idMicroPlan) {
        return observacionEntityToDtoConverter.convert(observacionRepository.findByMicroPlanIdMicroPlan(idMicroPlan));
    }

    @Override
    public List<Observacion> crearObservaciones(List<ObservacionDto> observaciones) {
        return observaciones.stream().map(this::crearObservacion).collect(Collectors.toList());
    }

    @Override
    public void actualizarObservacionesMicroPlan(List<ObservacionDto> observacionesDtos, MicroPlan microPlan) {

        final var mapObservacionExistenteIdObservacionActualizada = observacionesDtos.stream()
                .filter(observacionDto -> Objects.nonNull(observacionDto.getIdObservacion()))
                .collect(Collectors.toMap(ObservacionDto::getIdObservacion, Function.identity()));

        microPlan.getObservaciones().removeIf(observacion -> !mapObservacionExistenteIdObservacionActualizada
                .containsKey(observacion.getIdObservacion()));

        microPlan.getObservaciones().forEach(observacion -> {
            var observacionDto = mapObservacionExistenteIdObservacionActualizada.get(observacion.getIdObservacion());

            observacion.setObservacion(observacionDto.getObservacion());
            observacion.setNumeroSemana(observacionDto.getNumeroSemana());
        });

        microPlan.addAllObservaciones(crearObservaciones(getObservacionesSinId(observacionesDtos)));
    }

    private List<ObservacionDto> getObservacionesSinId(List<ObservacionDto> observacionDtos){
        return observacionDtos.stream()
                .filter(observacionDto -> Objects.isNull(observacionDto.getIdObservacion()))
                .toList();
    }

    private Observacion crearObservacion(ObservacionDto observacionDto){
        var observacion = new Observacion();

        observacion.setObservacion(observacionDto.getObservacion());
        observacion.setNumeroSemana(observacionDto.getNumeroSemana());

        return observacion;
    }
}
