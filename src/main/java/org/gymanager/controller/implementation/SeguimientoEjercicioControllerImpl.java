package org.gymanager.controller.implementation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.gymanager.controller.specification.SeguimientoEjercicioController;
import org.gymanager.model.client.SeguimientoEjercicioDto;
import org.gymanager.model.client.SeguimientoEjercicioRequestDto;
import org.gymanager.model.enums.SeguimientosFilter;
import org.gymanager.service.specification.SeguimientoEjercicioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SeguimientoEjercicioControllerImpl implements SeguimientoEjercicioController {

    @NonNull
    private SeguimientoEjercicioService seguimientoEjercicioService;

    @Override
    public ResponseEntity<Long> addSeguimientoEjercicio(Long idPlan, Long idEjercicioAplicado,
                                                        SeguimientoEjercicioRequestDto seguimientoEjercicioRequestDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(seguimientoEjercicioService.addSeguimientoEjercicio(
                        idPlan,
                        idEjercicioAplicado,
                        seguimientoEjercicioRequestDto));
    }

    @Override
    public ResponseEntity<List<SeguimientoEjercicioDto>> getSeguimientoEjercicioByIdRutina(Long idPlan,
                                                                                           Long idMicroPlan,
                                                                                           Long idRutina,
                                                                                           SeguimientosFilter seguimientosFilter) {
        return ResponseEntity.ok(seguimientoEjercicioService.getSeguimientoEjercicioByIdRutina(
                idPlan,
                idMicroPlan,
                idRutina,
                seguimientosFilter
        ));
    }
}
