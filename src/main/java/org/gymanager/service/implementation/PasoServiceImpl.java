package org.gymanager.service.implementation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gymanager.converter.PasoEntityToDtoConverter;
import org.gymanager.model.client.PasoDto;
import org.gymanager.repository.specification.PasoRepository;
import org.gymanager.service.specification.PasoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PasoServiceImpl implements PasoService {

    @NonNull
    private PasoRepository pasoRepository;

    @NonNull
    private PasoEntityToDtoConverter pasoEntityToDtoConverter;

    @Override
    public List<PasoDto> getPasosByIdEjercicio(Long idEjercicio) {
        return pasoEntityToDtoConverter.convert(pasoRepository.findByEjercicioIdEjercicio(idEjercicio));
    }
}
