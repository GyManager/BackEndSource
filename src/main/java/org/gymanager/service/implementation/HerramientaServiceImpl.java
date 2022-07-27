package org.gymanager.service.implementation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gymanager.converter.HerramientaEntityToDtoConverter;
import org.gymanager.model.client.HerramientaDto;
import org.gymanager.repository.specification.HerramientaRepository;
import org.gymanager.service.specification.HerramientaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class HerramientaServiceImpl implements HerramientaService {

    @NonNull
    private HerramientaRepository herramientaRepository;

    @NonNull
    private HerramientaEntityToDtoConverter herramientaEntityToDtoConverter;

    @Override
    public List<HerramientaDto> getHerramientasByIdEjercicio(Long idEjercicio) {
        return herramientaEntityToDtoConverter.convert(herramientaRepository.findByEjerciciosIdEjercicio(idEjercicio));
    }
}
