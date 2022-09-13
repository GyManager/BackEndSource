package org.gymanager.model.client;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
public class PlanDtoDetails extends PlanDto{

    @Valid
    @NotEmpty(message = "El plan debe tener al menos un micro plan asignado")
    private List<MicroPlanDtoDetails> microPlans;
}
