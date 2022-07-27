package org.gymanager.service.implementation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gymanager.converter.HerramientaEntityToDtoConverter;
import org.gymanager.model.client.HerramientaDto;
import org.gymanager.model.domain.Herramienta;
import org.gymanager.repository.specification.HerramientaRepository;
import org.gymanager.service.specification.HerramientaService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Override
    public List<Herramienta> getHerramientasByIds(List<Long> idHerramientaList) {
        if(Objects.isNull(idHerramientaList) || idHerramientaList.isEmpty()){
            return new ArrayList<>();
        }

        return herramientaRepository.findAllById(idHerramientaList);
    }
}
