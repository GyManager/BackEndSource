package org.gymanager.converter;

import lombok.RequiredArgsConstructor;
import org.gymanager.model.client.MicroPlanDto;
import org.gymanager.model.domain.MicroPlan;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MicroPlanEntityToDtoConverter implements GyManagerConverter<MicroPlan, MicroPlanDto> {

    @Override
    public MicroPlanDto convert(MicroPlan source) {
        var microPlanDto = new MicroPlanDto();
        microPlanDto.setIdMicroPlan(source.getIdMicroPlan());
        microPlanDto.setNombre(source.getNombre());
        microPlanDto.setEsTemplate(source.getEsTemplate());
        microPlanDto.setNumeroOrden(source.getNumeroOrden());
        return microPlanDto;
    }
}
