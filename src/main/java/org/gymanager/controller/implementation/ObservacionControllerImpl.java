package org.gymanager.controller.implementation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.gymanager.controller.specification.ObservacionController;
import org.gymanager.model.client.ObservacionDto;
import org.gymanager.service.specification.ObservacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ObservacionControllerImpl implements ObservacionController {

    @NonNull
    private ObservacionService observacionService;

    @Override
    public ResponseEntity<List<ObservacionDto>> getObservacionesByIdMicroPlan(Long idMicroPlan) {
        return ResponseEntity.ok(observacionService.getObservacionesByIdMicroPlan(idMicroPlan));
    }
}
