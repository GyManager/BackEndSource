package org.gymanager.model.client;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import java.util.List;

@Getter
@Setter
public class PlanDtoDetails extends PlanDto{

    @Valid
    private List<MicroPlanDtoDetails> microPlans;
}
