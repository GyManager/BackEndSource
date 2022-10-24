package org.gymanager.controller.implementation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.gymanager.controller.specification.SeguimientoController;
import org.gymanager.model.client.SeguimientoFinDiaDto;
import org.gymanager.model.client.SeguimientoFinDiaDtoDetail;
import org.gymanager.model.client.SeguimientoPlanDto;
import org.gymanager.model.enums.SeguimientosFilter;
import org.gymanager.service.specification.SeguimientoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SeguimientoControllerImpl implements SeguimientoController {

    @NonNull
    private SeguimientoService seguimientoService;

    @Override
    public ResponseEntity<Long> addSeguimientoEjercicio(Long idPlan, Long idMicroPlan, Long idRutina, SeguimientoFinDiaDto seguimientoFinDiaDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(seguimientoService.addSeguimientoEjercicio(
                        idPlan,
                        idMicroPlan,
                        idRutina,
                        seguimientoFinDiaDto
                ));
    }

    @Override
    public ResponseEntity<List<SeguimientoFinDiaDtoDetail>> getSeguimientoFinDiaByIdMicroPlan(Long idPlan,
                                                                                              Long idMicroPlan,
                                                                                              SeguimientosFilter seguimientosFilter) {
        return ResponseEntity.ok(seguimientoService.getSeguimientoFinDiaByIdMicroPlan(
                idPlan,
                idMicroPlan,
                seguimientosFilter
        ));
    }

    @Override
    public ResponseEntity<Void> addSeguimientoPlan(Long idPlan, SeguimientoPlanDto seguimientoPlanDto) {
        seguimientoService.addSeguimientoPlan(idPlan,seguimientoPlanDto);
        return ResponseEntity.noContent().build();
    }
}
