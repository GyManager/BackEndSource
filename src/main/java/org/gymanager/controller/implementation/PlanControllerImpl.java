package org.gymanager.controller.implementation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.gymanager.controller.specification.PlanController;
import org.gymanager.model.client.ClientePlanResumenDto;
import org.gymanager.model.client.PlanDto;
import org.gymanager.model.client.PlanDtoDetails;
import org.gymanager.model.enums.PlanesFilter;
import org.gymanager.service.specification.PlanService;
import org.gymanager.utils.Permisos;
import org.gymanager.utils.UserPermissionValidation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PlanControllerImpl implements PlanController {

    @NonNull
    private PlanService planService;

    @Override
    public ResponseEntity<List<PlanDto>> getPlansByIdCliente(Long idCliente, PlanesFilter planesFilter) {
        var validateUser = !UserPermissionValidation.userHasPermission(
                SecurityContextHolder.getContext().getAuthentication(),
                Permisos.GET_PLANES);

        return ResponseEntity.ok(planService.getPlansByIdCliente(idCliente, planesFilter, validateUser));
    }

    @Override
    public ResponseEntity<PlanDto> getPlanById(Long idPlan) {
        var validateUser = !UserPermissionValidation.userHasPermission(
                SecurityContextHolder.getContext().getAuthentication(),
                Permisos.GET_PLANES);

        return ResponseEntity.ok(planService.getPlanById(idPlan, validateUser));
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


    @Override
    public ResponseEntity<ClientePlanResumenDto> getResumenPlanesClienteById(Long idCliente) {
        return ResponseEntity.ok(planService.getResumenPlanesClienteById(idCliente));
    }

}
