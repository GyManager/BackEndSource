package org.gymanager.service.implementation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gymanager.converter.MicroPlanEntityToDtoConverter;
import org.gymanager.model.client.MicroPlanDto;
import org.gymanager.model.client.MicroPlanDtoRequest;
import org.gymanager.model.domain.MicroPlan;
import org.gymanager.model.enums.MicroPlanSortBy;
import org.gymanager.model.page.GyManagerPage;
import org.gymanager.repository.filters.MicroPlanSpecification;
import org.gymanager.repository.specification.MicroPlanRepository;
import org.gymanager.service.specification.MicroPlanService;
import org.gymanager.service.specification.RutinaService;
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
public class MicroPlanServiceImpl implements MicroPlanService {

    private static final String MICRO_PLAN_NO_ENCONTRADO = "Micro plan no encontrado";

    @NonNull
    private MicroPlanRepository microPlanRepository;

    @NonNull
    private MicroPlanEntityToDtoConverter microPlanEntityToDtoConverter;

    @NonNull
    private RutinaService rutinaService;

    @Override
    public GyManagerPage<MicroPlanDto> getMicroPlanes(String search, Integer page, Integer pageSize,
                                                                      MicroPlanSortBy sortBy, Sort.Direction direction) {
        var microPlanSpecification = new MicroPlanSpecification();
        microPlanSpecification.setSearch(search);

        Sort sort = sortBy.equals(MicroPlanSortBy.NONE) ? Sort.unsorted() : Sort.by(direction, sortBy.getField());
        PageRequest pageable = PageRequest.of(page, pageSize, sort);

        return new GyManagerPage<>(microPlanRepository.findAll(microPlanSpecification, pageable)
                .map(microPlanEntityToDtoConverter::convert));
    }

    @Override
    public MicroPlanDto getMicroPlanById(Long idMicroPlan) {
        return microPlanEntityToDtoConverter.convert(getMicroPlanEntityById(idMicroPlan));
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
    @Transactional
    public Long addMicroPlan(MicroPlanDtoRequest microPlanDtoRequest) {
        var microPlan = new MicroPlan();

        var rutinas = rutinaService.crearRutinas(microPlanDtoRequest.getRutinas());

        microPlan.setNombre(microPlanDtoRequest.getNombre());
        microPlan.setEsTemplate(Boolean.TRUE.equals(microPlanDtoRequest.getEsTemplate()));
        microPlan.setRutinas(rutinas);
        microPlan.setNumeroOrden(microPlanDtoRequest.getNumeroOrden());

        return microPlanRepository.save(microPlan).getIdMicroPlan();
    }

    @Override
    @Transactional
    public void updateMicroPlanById(Long idMicroPlan, MicroPlanDtoRequest microPlanDtoRequest) {
        var microPlan = getMicroPlanEntityById(idMicroPlan);

        rutinaService.actualizarRutinasMicroPlan(microPlanDtoRequest.getRutinas(), microPlan);

        microPlan.setNombre(microPlanDtoRequest.getNombre());
        microPlan.setEsTemplate(Boolean.TRUE.equals(microPlanDtoRequest.getEsTemplate()));
        microPlan.setNumeroOrden(microPlanDtoRequest.getNumeroOrden());

        microPlanRepository.save(microPlan);
    }
}
