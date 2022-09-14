package org.gymanager.controller.implementation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.gymanager.controller.specification.PlanController;
import org.gymanager.model.client.PlanDto;
import org.gymanager.model.client.PlanDtoDetails;
import org.gymanager.model.enums.PlanesFilter;
import org.gymanager.service.specification.PlanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PlanControllerImpl implements PlanController {

    @NonNull
    private PlanService planService;

    @Override
    public ResponseEntity<List<PlanDto>> getPlansByIdCliente(Long idCliente, PlanesFilter planesFilter) {
        return ResponseEntity.ok(planService.getPlansByIdCliente(idCliente, planesFilter));
    }

    @Override
    public ResponseEntity<PlanDto> getPlanById(Long idPlan) {
        return ResponseEntity.ok(planService.getPlanById(idPlan));
    }

    @Override
    public ResponseEntity<Long> addPlan(Long idCliente, PlanDtoDetails planDtoDetails) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(planService.addPlan(idCliente, planDtoDetails));
    }

    @Override
    public ResponseEntity<Void> updatePlanById(Long idCliente, Long idPlan, PlanDtoDetails planDtoDetails) {
        planService.updatePlanById(idCliente, idPlan, planDtoDetails);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> deletePlanById(Long idCliente, Long idPlan) {
        planService.deletePlanById(idCliente, idPlan);
        return ResponseEntity.noContent().build();
    }

}
