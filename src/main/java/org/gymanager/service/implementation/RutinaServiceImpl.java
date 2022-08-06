package org.gymanager.service.implementation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gymanager.converter.RutinaEntityToDtoConverter;
import org.gymanager.model.client.RutinaDto;
import org.gymanager.repository.specification.RutinaRepository;
import org.gymanager.service.specification.RutinaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RutinaServiceImpl implements RutinaService {

    @NonNull
    private RutinaRepository rutinaRepository;

    @NonNull
    private RutinaEntityToDtoConverter rutinaEntityToDtoConverter;

    @Override
    public List<RutinaDto> getRutinasByIdMicroPlan(Long idMicroPlan) {
        return rutinaEntityToDtoConverter.convert(rutinaRepository.findByMicroPlanIdMicroPlan(idMicroPlan));
    }
}
