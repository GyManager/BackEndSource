package org.gymanager.converter;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.gymanager.model.client.MicroPlanDtoDetails;
import org.gymanager.model.domain.MicroPlan;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class MicroPlanEntityToDtoDetailsConverter implements GyManagerConverter<MicroPlan, MicroPlanDtoDetails> {

    @NonNull
    private RutinaEntityToDtoDetailsConverter rutinaEntityToDtoDetailsConverter;

    @NonNull
    private ObservacionEntityToDtoConverter observacionEntityToDtoConverter;

    @Override
    public MicroPlanDtoDetails convert(MicroPlan source) {
        var microPlanDtoDetails = new MicroPlanDtoDetails();
        microPlanDtoDetails.setIdMicroPlan(source.getIdMicroPlan());
        microPlanDtoDetails.setNombre(source.getNombre());
        microPlanDtoDetails.setEsTemplate(source.getEsTemplate());
        microPlanDtoDetails.setNumeroOrden(source.getNumeroOrden());
        microPlanDtoDetails.setCantidadRutinas(source.getCantidadRutinas());
        microPlanDtoDetails.setRutinas(Objects.isNull(source.getRutinas()) ? null :
                rutinaEntityToDtoDetailsConverter.convert(source.getRutinas()));
        microPlanDtoDetails.setObservaciones(Objects.isNull(source.getObservaciones()) ? null :
                observacionEntityToDtoConverter.convert(source.getObservaciones()));
        return microPlanDtoDetails;
    }
}
