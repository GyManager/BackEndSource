package org.gymanager.controller.implementation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.gymanager.controller.specification.EjercicioController;
import org.gymanager.model.client.EjercicioDto;
import org.gymanager.model.client.EjercicioDtoRequest;
import org.gymanager.model.enums.EjercicioSortBy;
import org.gymanager.model.page.GyManagerPage;
import org.gymanager.service.specification.EjercicioService;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EjercicioControllerImpl implements EjercicioController {

    @NonNull
    private EjercicioService ejercicioService;

    @Override
    public ResponseEntity<GyManagerPage<EjercicioDto>> getEjercicios(String search, Boolean excluirEliminados,
                                                                     Integer page, Integer pageSize,
                                                                     EjercicioSortBy sortBy, Sort.Direction direction) {
        return ResponseEntity.ok(ejercicioService.getEjercicios(search, excluirEliminados, page, pageSize, sortBy, direction));
    }

    @Override
    public ResponseEntity<EjercicioDto> getEjercicioById(Long idEjercicio) {
        return ResponseEntity.ok(ejercicioService.getEjercicioById(idEjercicio));
    }

    @Override
    public ResponseEntity<Long> addEjercicio(EjercicioDtoRequest ejercicioDtoRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ejercicioService.addEjercicio(ejercicioDtoRequest));
    }

    @Override
    public ResponseEntity<Void> updateEjercicioById(Long idEjercicio, EjercicioDtoRequest ejercicioDtoRequest) {
        ejercicioService.updateEjercicioById(idEjercicio, ejercicioDtoRequest);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> deleteEjercicioById(Long idEjercicio) {
        ejercicioService.deleteEjercicioById(idEjercicio);
        return ResponseEntity.noContent().build();
    }
}
