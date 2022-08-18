package org.gymanager.controller.implementation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.gymanager.controller.specification.PasoController;
import org.gymanager.model.client.PasoDto;
import org.gymanager.service.specification.PasoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PasoControllerImpl implements PasoController {

    @NonNull
    private PasoService pasoService;

    @Override
    public ResponseEntity<List<PasoDto>> getPasosByIdEjercicio(Long idEjercicio) {
        return ResponseEntity.ok(pasoService.getPasosByIdEjercicio(idEjercicio));
    }
}
