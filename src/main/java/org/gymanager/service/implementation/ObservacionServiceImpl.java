package org.gymanager.service.implementation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gymanager.converter.ObservacionEntityToDtoConverter;
import org.gymanager.model.client.ObservacionDto;
import org.gymanager.repository.specification.ObservacionRepository;
import org.gymanager.service.specification.ObservacionService;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
