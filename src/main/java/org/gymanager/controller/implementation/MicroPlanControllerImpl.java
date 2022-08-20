package org.gymanager.controller.implementation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.gymanager.controller.specification.MicroPlanController;
import org.gymanager.model.client.MicroPlanDto;
import org.gymanager.model.client.MicroPlanDtoDetails;
import org.gymanager.model.enums.MicroPlanSortBy;
import org.gymanager.model.page.GyManagerPage;
import org.gymanager.service.specification.MicroPlanService;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MicroPlanControllerImpl implements MicroPlanController {

    @NonNull
    private MicroPlanService microPlanService;

    @Override
    public ResponseEntity<GyManagerPage<MicroPlanDto>> getMicroPlanes(String search, Integer page, Integer pageSize,
                                                                      MicroPlanSortBy sortBy, Sort.Direction direction) {
        return ResponseEntity.ok(microPlanService.getMicroPlanes(search, page, pageSize, sortBy, direction));
    }

    @Override
    public ResponseEntity<MicroPlanDtoDetails> getMicroPlanById(Long idMicroPlan) {
        return ResponseEntity.ok(microPlanService.getMicroPlanById(idMicroPlan));
    }

    @Override
    public ResponseEntity<Long> addMicroPlan(MicroPlanDtoDetails microPlanDtoDetails) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(microPlanService.addMicroPlan(microPlanDtoDetails));
    }

    @Override
    public ResponseEntity<Void> updateMicroPlanById(Long idMicroPlan, MicroPlanDtoDetails microPlanDtoDetails) {
        microPlanService.updateMicroPlanById(idMicroPlan, microPlanDtoDetails);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> deleteMicroPlanById(Long idMicroPlan) {
        microPlanService.deleteMicroPlanById(idMicroPlan);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<MicroPlanDto>> getMicroPlanesByIdPlan(Long idPlan) {
        return ResponseEntity.ok(microPlanService.getMicroPlanesByIdPlan(idPlan));
    }
}
