package org.gymanager.service.specification;

import org.gymanager.model.domain.EstadoSeguimiento;

import java.util.List;

public interface EstadoSeguimientoService {

    EstadoSeguimiento getEstadoSeguimientoById(Long idEstadoSeguimiento);

    List<EstadoSeguimiento> getEstadoSeguimientos();
}
