package org.gymanager.controller.implementation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.gymanager.controller.specification.EjercicioAplicadoController;
import org.gymanager.model.client.EjercicioAplicadoDto;
import org.gymanager.service.specification.EjercicioAplicadoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class EjercicioAplicadoControllerImpl implements EjercicioAplicadoController {

    @NonNull
    private EjercicioAplicadoService ejercicioAplicadoService;

    @Override
    public ResponseEntity<List<EjercicioAplicadoDto>> getEjerciciosAplicadosByIdRutina(Long idMicroPlan, Long idRutina) {
        return ResponseEntity.ok(ejercicioAplicadoService.getEjerciciosAplicadosByIdRutina(idMicroPlan, idRutina));
    }
}
