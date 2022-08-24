package org.gymanager.service.specification;

import org.gymanager.model.client.ObservacionDto;
import org.gymanager.model.domain.MicroPlan;
import org.gymanager.model.domain.Observacion;

import java.util.List;

public interface ObservacionService {

    List<ObservacionDto> getObservacionesByIdMicroPlan(Long idMicroPlan);

    List<Observacion> crearObservaciones(List<ObservacionDto> observaciones);

    void actualizarObservacionesMicroPlan(List<ObservacionDto> observaciones, MicroPlan microPlan);
}
