package org.gymanager.service.specification;

import org.gymanager.model.client.RutinaDto;
import org.gymanager.model.client.RutinaDtoDetails;
import org.gymanager.model.domain.MicroPlan;
import org.gymanager.model.domain.Rutina;

import java.util.List;

public interface RutinaService {

    List<RutinaDto> getRutinasByIdMicroPlan(Long idMicroPlan);

    List<Rutina> crearRutinas(List<RutinaDtoDetails> rutinas);

    void actualizarRutinasMicroPlan(List<RutinaDtoDetails> rutinas, MicroPlan microPlan);
}
