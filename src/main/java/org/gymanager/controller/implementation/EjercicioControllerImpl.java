package org.gymanager.controller.implementation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.gymanager.controller.specification.EjercicioController;
import org.gymanager.model.client.EjercicioDto;
import org.gymanager.service.specification.EjercicioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class EjercicioControllerImpl implements EjercicioController {

    @NonNull
    private EjercicioService ejercicioService;

    @Override
    public ResponseEntity<List<EjercicioDto>> getEjercicios() {
        return ResponseEntity.ok(ejercicioService.getEjercicios());
    }

    @Override
    public ResponseEntity<EjercicioDto> getEjercicioById(Long idEjercicio) {
        return ResponseEntity.ok(ejercicioService.getEjercicioById(idEjercicio));
    }
}
