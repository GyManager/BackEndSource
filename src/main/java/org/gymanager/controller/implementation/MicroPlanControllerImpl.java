package org.gymanager.controller.implementation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.gymanager.controller.specification.MicroPlanController;
import org.gymanager.model.client.MicroPlanDto;
import org.gymanager.model.enums.MicroPlanSortBy;
import org.gymanager.model.page.GyManagerPage;
import org.gymanager.service.specification.MicroPlanService;
import org.springframework.data.domain.Sort;
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
}
