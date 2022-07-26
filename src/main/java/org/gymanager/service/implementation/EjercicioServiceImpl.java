package org.gymanager.service.implementation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gymanager.converter.EjercicioEntityToDtoConverter;
import org.gymanager.model.client.EjercicioDto;
import org.gymanager.model.domain.Ejercicio;
import org.gymanager.model.enums.EjercicioSortBy;
import org.gymanager.model.page.GyManagerPage;
import org.gymanager.repository.filters.EjercicioSpecification;
import org.gymanager.repository.specification.EjercicioRepository;
import org.gymanager.service.specification.EjercicioService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
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
    public GyManagerPage<EjercicioDto> getEjercicios(String search, Integer page, Integer pageSize,
                                            EjercicioSortBy sortBy, Sort.Direction direction) {
        var ejercicioSpecification = new EjercicioSpecification();
        ejercicioSpecification.setNombreEjercicioOrTipoEjercicio(search);

        Sort sort = sortBy.equals(EjercicioSortBy.NONE) ? Sort.unsorted() : Sort.by(direction, sortBy.getField());
        PageRequest pageable = PageRequest.of(page, pageSize, sort);

        return new GyManagerPage<>(ejercicioRepository.findAll(ejercicioSpecification, pageable)
                .map(ejercicioEntityToDtoConverter::convert));
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
