package org.gymanager.service.specification;

import org.gymanager.model.client.RutinaDto;
import org.gymanager.model.client.RutinaDtoRequest;
import org.gymanager.model.domain.MicroPlan;
import org.gymanager.model.domain.Rutina;

import java.util.List;

public interface RutinaService {

    List<RutinaDto> getRutinasByIdMicroPlan(Long idMicroPlan);

    List<Rutina> crearRutinas(List<RutinaDtoRequest> rutinas);

    void actualizarRutinasMicroPlan(List<RutinaDtoRequest> rutinas, MicroPlan microPlan);
}
