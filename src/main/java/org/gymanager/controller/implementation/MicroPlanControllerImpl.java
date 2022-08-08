package org.gymanager.controller.implementation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.gymanager.controller.specification.MicroPlanController;
import org.gymanager.model.client.MicroPlanDto;
import org.gymanager.model.client.MicroPlanDtoRequest;
import org.gymanager.model.enums.MicroPlanSortBy;
import org.gymanager.model.page.GyManagerPage;
import org.gymanager.service.specification.MicroPlanService;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<MicroPlanDto> getMicroPlanById(Long idMicroPlan) {
        return ResponseEntity.ok(microPlanService.getMicroPlanById(idMicroPlan));
    }

    @Override
    public ResponseEntity<Long> addMicroPlan(MicroPlanDtoRequest microPlanDtoRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(microPlanService.addMicroPlan(microPlanDtoRequest));
    }

    @Override
    public ResponseEntity<Void> updateMicroPlanById(Long idMicroPlan, MicroPlanDtoRequest microPlanDtoRequest) {
        microPlanService.updateMicroPlanById(idMicroPlan, microPlanDtoRequest);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> deleteMicroPlanById(Long idMicroPlan) {
        microPlanService.deleteMicroPlanById(idMicroPlan);
        return ResponseEntity.noContent().build();
    }
}
