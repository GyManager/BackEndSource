package org.gymanager.service.specification;

import org.gymanager.model.client.ObservacionDto;

import java.util.List;

public interface ObservacionService {

    List<ObservacionDto> getObservacionesByIdMicroPlan(Long idMicroPlan);
}
