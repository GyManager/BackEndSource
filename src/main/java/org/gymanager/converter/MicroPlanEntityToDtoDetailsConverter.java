package org.gymanager.converter;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.gymanager.model.client.MicroPlanDtoDetails;
import org.gymanager.model.domain.MicroPlan;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MicroPlanEntityToDtoDetailsConverter implements GyManagerConverter<MicroPlan, MicroPlanDtoDetails> {

    @NonNull
    private RutinaEntityToDtoDetailsConverter rutinaEntityToDtoDetailsConverter;

    @Override
    public MicroPlanDtoDetails convert(MicroPlan source) {
        var microPlanDtoDetails = new MicroPlanDtoDetails();
        microPlanDtoDetails.setIdMicroPlan(source.getIdMicroPlan());
        microPlanDtoDetails.setNombre(source.getNombre());
        microPlanDtoDetails.setEsTemplate(source.getEsTemplate());
        microPlanDtoDetails.setNumeroOrden(source.getNumeroOrden());
        microPlanDtoDetails.setRutinas(rutinaEntityToDtoDetailsConverter.convert(source.getRutinas()));
        return microPlanDtoDetails;
    }
}
