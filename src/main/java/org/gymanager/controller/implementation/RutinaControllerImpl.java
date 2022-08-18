package org.gymanager.controller.implementation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.gymanager.controller.specification.RutinaController;
import org.gymanager.model.client.RutinaDto;
import org.gymanager.service.specification.RutinaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RutinaControllerImpl implements RutinaController {

    @NonNull
    private RutinaService rutinaService;

    @Override
    public ResponseEntity<List<RutinaDto>> getRutinasByIdMicroPlan(Long idMicroPlan) {
        return ResponseEntity.ok(rutinaService.getRutinasByIdMicroPlan(idMicroPlan));
    }
}
