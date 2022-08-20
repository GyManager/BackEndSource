package org.gymanager.controller.implementation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.gymanager.controller.specification.PlanController;
import org.gymanager.model.client.PlanDto;
import org.gymanager.model.domain.Plan;
import org.gymanager.service.specification.PlanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PlanControllerImpl implements PlanController {

    @NonNull
    private PlanService planService;

    @Override
    public ResponseEntity<List<PlanDto>> getPlansByClientId(Long idCliente) {
        return ResponseEntity.ok(planService.getPlansByClientId(idCliente));
    }

    @Override
    public ResponseEntity<PlanDto> getPlanById(Long idPlan) {
        return ResponseEntity.ok(planService.getPlanById(idPlan));
    }
}
