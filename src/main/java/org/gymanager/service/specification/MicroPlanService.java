package org.gymanager.service.specification;

import org.gymanager.model.client.MicroPlanDto;
import org.gymanager.model.client.MicroPlanDtoRequest;
import org.gymanager.model.domain.MicroPlan;
import org.gymanager.model.enums.MicroPlanSortBy;
import org.gymanager.model.page.GyManagerPage;
import org.springframework.data.domain.Sort;

public interface MicroPlanService {

    GyManagerPage<MicroPlanDto> getMicroPlanes(String search, Integer page, Integer pageSize,
                                                               MicroPlanSortBy sortBy, Sort.Direction direction);

    MicroPlanDto getMicroPlanById(Long idMicroPlan);

    MicroPlan getMicroPlanEntityById(Long idMicroPlan);

    Long addMicroPlan(MicroPlanDtoRequest microPlanDtoRequest);

    void updateMicroPlanById(Long idMicroPlan, MicroPlanDtoRequest microPlanDtoRequest);

    void deleteMicroPlanById(Long idMicroPlan);
}
