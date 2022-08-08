package org.gymanager.service.implementation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gymanager.converter.RutinaEntityToDtoConverter;
import org.gymanager.model.client.RutinaDto;
import org.gymanager.model.client.RutinaDtoRequest;
import org.gymanager.model.domain.Rutina;
import org.gymanager.repository.specification.RutinaRepository;
import org.gymanager.service.specification.EjercicioAplicadoService;
import org.gymanager.service.specification.RutinaService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RutinaServiceImpl implements RutinaService {

    @NonNull
    private RutinaRepository rutinaRepository;

    @NonNull
    private RutinaEntityToDtoConverter rutinaEntityToDtoConverter;

    @NonNull
    private EjercicioAplicadoService ejercicioAplicadoService;

    @Override
    public List<RutinaDto> getRutinasByIdMicroPlan(Long idMicroPlan) {
        return rutinaEntityToDtoConverter.convert(rutinaRepository.findByMicroPlanIdMicroPlan(idMicroPlan));
    }

    @Override
    public List<Rutina> crearRutinas(List<RutinaDtoRequest> rutinas) {
        return rutinas.stream().map(this::crearRutina).collect(Collectors.toList());
    }

    private Rutina crearRutina(RutinaDtoRequest rutinaDtoRequest) {
        var rutina = new Rutina();

        var ejerciciosAplicados = ejercicioAplicadoService.crearEjerciciosAplicados(rutinaDtoRequest.getEjerciciosAplicados());

        rutina.setNombre(rutinaDtoRequest.getNombre());
        rutina.setEsTemplate(Boolean.TRUE.equals(rutinaDtoRequest.getEsTemplate()));
        rutina.setEjercicioAplicados(ejerciciosAplicados);

        return rutina;
    }
}
