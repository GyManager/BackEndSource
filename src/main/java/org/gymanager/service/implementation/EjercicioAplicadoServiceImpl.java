package org.gymanager.service.implementation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gymanager.converter.EjercicioAplicadoEntityToDtoConverter;
import org.gymanager.model.client.EjercicioAplicadoDto;
import org.gymanager.repository.specification.EjercicioAplicadoRepository;
import org.gymanager.service.specification.EjercicioAplicadoService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EjercicioAplicadoServiceImpl implements EjercicioAplicadoService {

    @NonNull
    private EjercicioAplicadoRepository ejercicioAplicadoRepository;

    @NonNull
    private EjercicioAplicadoEntityToDtoConverter ejercicioAplicadoEntityToDtoConverter;

    @Override
    @Transactional
    public List<EjercicioAplicadoDto> getEjerciciosAplicadosByIdRutina(Long idMicroPlan, Long idRutina) {
        return ejercicioAplicadoEntityToDtoConverter.convert(ejercicioAplicadoRepository.findByRutinaIdRutina(idRutina));
    }
}
