package org.gymanager.service.implementation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gymanager.converter.EjercicioEntityToDtoConverter;
import org.gymanager.model.client.EjercicioDto;
import org.gymanager.model.domain.Ejercicio;
import org.gymanager.repository.specification.EjercicioRepository;
import org.gymanager.service.specification.EjercicioService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class EjercicioServiceImpl implements EjercicioService {

    private static final String EJERCICIO_NO_ENCONTRADO = "Ejercicio no encontrado";

    @NonNull
    private EjercicioRepository ejercicioRepository;

    @NonNull
    private EjercicioEntityToDtoConverter ejercicioEntityToDtoConverter;


    @Override
    @Transactional
    public List<EjercicioDto> getEjercicios() {
        return ejercicioEntityToDtoConverter.convert(ejercicioRepository.findAll());
    }

    @Override
    @Transactional
    public EjercicioDto getEjercicioById(Long idEjercicio) {
        return ejercicioEntityToDtoConverter.convert(getEjercicioEntityById(idEjercicio));
    }

    @Override
    public Ejercicio getEjercicioEntityById(Long idEjercicio){
        Optional<Ejercicio> ejercicio = ejercicioRepository.findById(idEjercicio);

        if(ejercicio.isEmpty()){
            log.error(EJERCICIO_NO_ENCONTRADO);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, EJERCICIO_NO_ENCONTRADO);
        }

        return ejercicio.get();
    }
}
