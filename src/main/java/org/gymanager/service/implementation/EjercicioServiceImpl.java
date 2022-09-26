package org.gymanager.service.implementation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gymanager.converter.EjercicioEntityToDtoConverter;
import org.gymanager.model.client.EjercicioDto;
import org.gymanager.model.client.EjercicioDtoRequest;
import org.gymanager.model.domain.Ejercicio;
import org.gymanager.model.domain.TipoEjercicio;
import org.gymanager.model.enums.EjercicioSortBy;
import org.gymanager.model.page.GyManagerPage;
import org.gymanager.repository.filters.EjercicioSpecification;
import org.gymanager.repository.specification.EjercicioRepository;
import org.gymanager.service.specification.EjercicioService;
import org.gymanager.service.specification.HerramientaService;
import org.gymanager.service.specification.PasoService;
import org.gymanager.service.specification.TipoEjercicioService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.apache.commons.lang3.BooleanUtils.isTrue;

@Service
@RequiredArgsConstructor
@Slf4j
public class EjercicioServiceImpl implements EjercicioService {

    private static final String EJERCICIO_NO_ENCONTRADO = "Ejercicio no encontrado";
    private static final String NOMBRE_TIPO_EJERCICIO_EN_USO = "Ya existe un ejercicio con el nombre (%s)" +
            " del tipo (%s)";

    @Value("${logical-delete}")
    private Boolean logicalDelete;

    @NonNull
    private EjercicioRepository ejercicioRepository;

    @NonNull
    private EjercicioEntityToDtoConverter ejercicioEntityToDtoConverter;

    @NonNull
    private TipoEjercicioService tipoEjercicioService;

    @NonNull
    private PasoService pasoService;

    @NonNull
    private HerramientaService herramientaService;

    @Override
    @Transactional
    public GyManagerPage<EjercicioDto> getEjercicios(String search, Boolean excluirEliminados,
                                                     Integer page, Integer pageSize, EjercicioSortBy sortBy,
                                                     Sort.Direction direction) {
        var ejercicioSpecification = new EjercicioSpecification();
        ejercicioSpecification.setNombreEjercicioOrTipoEjercicio(search);
        ejercicioSpecification.setExcluirEliminados(excluirEliminados);

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

    @Override
    @Transactional
    public Long addEjercicio(EjercicioDtoRequest ejercicioDtoRequest) {
        var ejercicio = new Ejercicio();

        var tipoEjercicio = tipoEjercicioService.getTipoEjercicioByNombre(ejercicioDtoRequest.getTipoEjercicio());
        validarEjercicioConTipoYNombreNoExiste(tipoEjercicio, ejercicioDtoRequest.getNombre());

        var herramientas = herramientaService.getHerramientasByIds(ejercicioDtoRequest.getIdHerramientaList());
        var pasos = pasoService.crearPasos(ejercicioDtoRequest.getPasos());

        ejercicio.setNombre(ejercicioDtoRequest.getNombre());
        ejercicio.setTipoEjercicio(tipoEjercicio);
        ejercicio.setVideo(ejercicioDtoRequest.getVideo());
        ejercicio.setHerramientas(herramientas);
        ejercicio.setPasos(pasos);

        return ejercicioRepository.save(ejercicio).getIdEjercicio();
    }

    @Override
    @Transactional
    public void updateEjercicioById(Long idEjercicio, EjercicioDtoRequest ejercicioDtoRequest) {
        var ejercicio = getEjercicioEntityById(idEjercicio);

        var tipoEjercicio = tipoEjercicioService.getTipoEjercicioByNombre(ejercicioDtoRequest.getTipoEjercicio());
        if(!ejercicio.getNombre().equals(ejercicioDtoRequest.getNombre()) ||
                !ejercicio.getTipoEjercicio().getNombre().equals(ejercicioDtoRequest.getTipoEjercicio())){
            validarEjercicioConTipoYNombreNoExiste(tipoEjercicio, ejercicioDtoRequest.getNombre());
        }

        var herramientas = herramientaService.getHerramientasByIds(ejercicioDtoRequest.getIdHerramientaList());

        pasoService.actualizarPasosEjercicio(ejercicioDtoRequest.getPasos(), ejercicio);

        ejercicio.setNombre(ejercicioDtoRequest.getNombre());
        ejercicio.setTipoEjercicio(tipoEjercicio);
        ejercicio.setVideo(ejercicioDtoRequest.getVideo());
        ejercicio.setHerramientas(herramientas);

        ejercicioRepository.save(ejercicio);
    }

    @Override
    public void deleteEjercicioById(Long idEjercicio) {
        var ejercicio = getEjercicioEntityById(idEjercicio);

        if(isTrue(logicalDelete)){
            ejercicio.setFechaBaja(LocalDateTime.now());
            ejercicioRepository.save(ejercicio);
        } else {
            ejercicioRepository.delete(ejercicio);
        }
    }

    private void validarEjercicioConTipoYNombreNoExiste(TipoEjercicio tipoEjercicio, String nombre){
        if(ejercicioRepository.findByTipoEjercicioAndNombreIgnoreCase(tipoEjercicio, nombre).isPresent()){
            log.error(String.format(NOMBRE_TIPO_EJERCICIO_EN_USO, nombre, tipoEjercicio.getNombre()));
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format(NOMBRE_TIPO_EJERCICIO_EN_USO, nombre, tipoEjercicio.getNombre()));
        }
    }
}
