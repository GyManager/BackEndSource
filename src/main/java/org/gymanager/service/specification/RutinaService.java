package org.gymanager.service.specification;

import org.gymanager.model.client.RutinaDto;

import java.util.List;

public interface RutinaService {

    List<RutinaDto> getRutinasByIdMicroPlan(Long idMicroPlan);
}
