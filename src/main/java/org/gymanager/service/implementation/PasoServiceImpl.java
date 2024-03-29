package org.gymanager.service.implementation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gymanager.converter.PasoEntityToDtoConverter;
import org.gymanager.model.client.PasoDto;
import org.gymanager.model.domain.Ejercicio;
import org.gymanager.model.domain.Paso;
import org.gymanager.repository.specification.PasoRepository;
import org.gymanager.service.specification.PasoService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PasoServiceImpl implements PasoService {

    private static final String NUMERO_DE_PASO_DEBE_SER_UNICO = "Hay pasos con numero de paso repetido, el numero de " +
            "paso debe ser unico para poder mostrarlos en ese orden.";

    @NonNull
    private PasoRepository pasoRepository;

    @NonNull
    private PasoEntityToDtoConverter pasoEntityToDtoConverter;

    @Override
    public List<PasoDto> getPasosByIdEjercicio(Long idEjercicio) {
        return pasoEntityToDtoConverter.convert(pasoRepository.findByEjercicioIdEjercicio(idEjercicio));
    }

    @Override
    public List<Paso> crearPasos(List<PasoDto> pasoDtos) {
        if(Objects.isNull(pasoDtos) || pasoDtos.isEmpty()){
            return new ArrayList<>();
        }

        validarListaDePasosNoRepiteNumeroDePaso(pasoDtos);

        return pasoDtos.stream().map(this::crearPaso).collect(Collectors.toList());
    }

    @Override
    public void actualizarPasosEjercicio(List<PasoDto> pasoDtoList, Ejercicio ejercicio) {
        if(Objects.isNull(pasoDtoList)){
            pasoDtoList = new ArrayList<>();
        }

        validarListaDePasosNoRepiteNumeroDePaso(pasoDtoList);

        final var mapPasoExistenteIdPasoActualizadoDto = pasoDtoList.stream()
                .filter(pasoDto -> Objects.nonNull(pasoDto.getIdPaso()))
                .collect(Collectors.toMap(PasoDto::getIdPaso, Function.identity()));

        ejercicio.getPasos().removeIf(paso -> !mapPasoExistenteIdPasoActualizadoDto.containsKey(paso.getIdPaso()));

        ejercicio.getPasos().forEach(paso -> {
            var pasoDtoActualizado = mapPasoExistenteIdPasoActualizadoDto.get(paso.getIdPaso());
            paso.setNumeroPaso(pasoDtoActualizado.getNumeroPaso());
            paso.setImagen(pasoDtoActualizado.getImagen());
            paso.setContenido(pasoDtoActualizado.getContenido());
        });

        ejercicio.addAllPasos(crearPasos(getPasosSinId(pasoDtoList)));
    }

    private List<PasoDto> getPasosSinId(List<PasoDto> pasoDtoList){
        return pasoDtoList.stream()
                .filter(pasoDto -> Objects.isNull(pasoDto.getIdPaso()))
                .toList();
    }

    private Paso crearPaso(PasoDto pasoDto){
        var paso = new Paso();

        paso.setNumeroPaso(pasoDto.getNumeroPaso());
        paso.setImagen(pasoDto.getImagen());
        paso.setContenido(pasoDto.getContenido());

        return paso;
    }

    private void validarListaDePasosNoRepiteNumeroDePaso(List<PasoDto> pasoDtos){
        long numeroDePasoUnicos = pasoDtos.stream().map(PasoDto::getNumeroPaso).distinct().count();

        if(numeroDePasoUnicos != pasoDtos.size()){
            log.error(NUMERO_DE_PASO_DEBE_SER_UNICO);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, NUMERO_DE_PASO_DEBE_SER_UNICO);
        }
    }
}
