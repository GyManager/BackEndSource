package org.gymanager.service.implementation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gymanager.converter.MicroPlanEntityToDtoConverter;
import org.gymanager.converter.MicroPlanEntityToDtoDetailsConverter;
import org.gymanager.model.client.MicroPlanDto;
import org.gymanager.model.client.MicroPlanDtoDetails;
import org.gymanager.model.domain.MicroPlan;
import org.gymanager.model.domain.Plan;
import org.gymanager.model.enums.MicroPlanSortBy;
import org.gymanager.model.page.GyManagerPage;
import org.gymanager.repository.filters.MicroPlanSpecification;
import org.gymanager.repository.specification.MicroPlanRepository;
import org.gymanager.service.specification.MicroPlanService;
import org.gymanager.service.specification.ObservacionService;
import org.gymanager.service.specification.RutinaService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.BooleanUtils.isTrue;

@Service
@RequiredArgsConstructor
@Slf4j
public class MicroPlanServiceImpl implements MicroPlanService {

    private static final String MICRO_PLAN_NO_ENCONTRADO = "Micro plan no encontrado";
    private static final String MICRO_PLAN_TEMPLATE_CON_NOMBRE_YA_EXISTE = "Ya existe un micro plan plantilla con el " +
            "nombre (%s) ingresado";

    @Value("${logical-delete}")
    private Boolean logicalDelete;

    @NonNull
    private MicroPlanRepository microPlanRepository;

    @NonNull
    private MicroPlanEntityToDtoConverter microPlanEntityToDtoConverter;

    @NonNull
    private MicroPlanEntityToDtoDetailsConverter microPlanEntityToDtoDetailsConverter;

    @NonNull
    private RutinaService rutinaService;

    @NonNull
    private ObservacionService observacionService;

    @Override
    public GyManagerPage<MicroPlanDto> getMicroPlanes(String search, Boolean esTemplate, Integer cantidadRutinas,
                                                      Integer page, Integer pageSize, MicroPlanSortBy sortBy, Sort.Direction direction) {
        var microPlanSpecification = new MicroPlanSpecification();
        microPlanSpecification.setSearch(search);
        microPlanSpecification.setEsTemplate(esTemplate);
        microPlanSpecification.setCantidadRutinas(cantidadRutinas);

        Sort sort = sortBy.equals(MicroPlanSortBy.NONE) ? Sort.unsorted() : Sort.by(direction, sortBy.getField());
        PageRequest pageable = PageRequest.of(page, pageSize, sort);

        return new GyManagerPage<>(microPlanRepository.findAll(microPlanSpecification, pageable)
                .map(microPlanEntityToDtoConverter::convert));
    }

    @Override
    public MicroPlanDtoDetails getMicroPlanById(Long idMicroPlan) {
        return microPlanEntityToDtoDetailsConverter.convert(getMicroPlanEntityById(idMicroPlan));
    }

    @Override
    public MicroPlan getMicroPlanEntityById(Long idMicroPlan){
        Optional<MicroPlan> microPlan = microPlanRepository.findById(idMicroPlan);

        if(microPlan.isEmpty()){
            log.error(MICRO_PLAN_NO_ENCONTRADO);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, MICRO_PLAN_NO_ENCONTRADO);
        }

        return microPlan.get();
    }

    @Override
    public List<MicroPlan> crearMicroPlanes(List<MicroPlanDtoDetails> microPlans) {
        return microPlans.stream().map(this::crearMicroPlan).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Long addMicroPlan(MicroPlanDtoDetails microPlanDtoDetails) {
        if(microPlanDtoDetails.getEsTemplate()){
            validarMicroPlanConNombreYTemplateNoExiste(microPlanDtoDetails.getNombre(), microPlanDtoDetails.getEsTemplate());
        }
        return crearMicroPlan(microPlanDtoDetails).getIdMicroPlan();
    }

    @Override
    @Transactional
    public void updateMicroPlanById(Long idMicroPlan, MicroPlanDtoDetails microPlanDtoDetails) {
        var microPlan = getMicroPlanEntityById(idMicroPlan);

        rutinaService.actualizarRutinasMicroPlan(microPlanDtoDetails.getRutinas(), microPlan);
        observacionService.actualizarObservacionesMicroPlan(microPlanDtoDetails.getObservaciones(), microPlan);

        if(!microPlan.getNombre().equals(microPlanDtoDetails.getNombre())){
            if(microPlanDtoDetails.getEsTemplate()) {
                validarMicroPlanConNombreYTemplateNoExiste(microPlanDtoDetails.getNombre(), microPlanDtoDetails.getEsTemplate());
            }
            microPlan.setNombre(microPlanDtoDetails.getNombre());
        }
        microPlan.setEsTemplate(Boolean.TRUE.equals(microPlanDtoDetails.getEsTemplate()));
        microPlan.setNumeroOrden(microPlanDtoDetails.getNumeroOrden());

        microPlanRepository.save(microPlan);
    }

    @Override
    public void deleteMicroPlanById(Long idMicroPlan) {
        var microPlan = getMicroPlanEntityById(idMicroPlan);

        if(isTrue(logicalDelete)){
            microPlan.setFechaBaja(LocalDateTime.now());
            microPlanRepository.save(microPlan);
        } else {
            microPlanRepository.delete(microPlan);
        }
    }

    @Override
    public List<MicroPlanDto> getMicroPlanesByIdPlan(Long idPlan) {
        return microPlanEntityToDtoConverter.convert(microPlanRepository.findByPlanIdPlan(idPlan));
    }

    @Override
    public void actualizarMicroPlanesPlan(List<MicroPlanDtoDetails> microPlanDtoDetailsList, Plan plan) {

        final var mapMicroPlanExistenteIdMPActualizadoDto = microPlanDtoDetailsList.stream()
                .filter(microPlanDtoDetails -> Objects.nonNull(microPlanDtoDetails.getIdMicroPlan()))
                .collect(Collectors.toMap(MicroPlanDtoDetails::getIdMicroPlan, Function.identity()));

        plan.getMicroPlans().removeIf(microPlan -> !mapMicroPlanExistenteIdMPActualizadoDto.containsKey(microPlan.getIdMicroPlan()));

        plan.getMicroPlans().forEach(microPlan -> {
            var MicroPlanDtoActualizado = mapMicroPlanExistenteIdMPActualizadoDto.get(microPlan.getIdMicroPlan());

            rutinaService.actualizarRutinasMicroPlan(MicroPlanDtoActualizado.getRutinas(), microPlan);
            observacionService.actualizarObservacionesMicroPlan(MicroPlanDtoActualizado.getObservaciones(), microPlan);

            microPlan.setNombre(MicroPlanDtoActualizado.getNombre());
            microPlan.setEsTemplate(Boolean.TRUE.equals(MicroPlanDtoActualizado.getEsTemplate()));
            microPlan.setNumeroOrden(MicroPlanDtoActualizado.getNumeroOrden());
        });

        plan.addAllMicroPlans(crearMicroPlanes(getMicroPlansSinId(microPlanDtoDetailsList)));
    }

    private List<MicroPlanDtoDetails> getMicroPlansSinId(List<MicroPlanDtoDetails> microPlanDtoDetailsList){
        return microPlanDtoDetailsList.stream()
                .filter(microPlanDtoDetails -> Objects.isNull(microPlanDtoDetails.getIdMicroPlan()))
                .toList();
    }

    private MicroPlan crearMicroPlan(MicroPlanDtoDetails microPlanDtoDetails){
        var microPlan = new MicroPlan();

        var rutinas = rutinaService.crearRutinas(microPlanDtoDetails.getRutinas());
        var observaciones = observacionService.crearObservaciones(microPlanDtoDetails.getObservaciones());

        microPlan.setNombre(microPlanDtoDetails.getNombre());
        microPlan.setEsTemplate(Boolean.TRUE.equals(microPlanDtoDetails.getEsTemplate()));
        microPlan.setRutinas(rutinas);
        microPlan.setObservaciones(observaciones);
        microPlan.setNumeroOrden(microPlanDtoDetails.getNumeroOrden());

        return microPlanRepository.save(microPlan);
    }

    private void validarMicroPlanConNombreYTemplateNoExiste(String nombre, Boolean esTemplate){
        if(!microPlanRepository.findByNombreIgnoreCaseAndEsTemplate(nombre, esTemplate).isEmpty()){
            log.error(String.format(MICRO_PLAN_TEMPLATE_CON_NOMBRE_YA_EXISTE, nombre));
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(MICRO_PLAN_TEMPLATE_CON_NOMBRE_YA_EXISTE, nombre));
        }
    }
}
