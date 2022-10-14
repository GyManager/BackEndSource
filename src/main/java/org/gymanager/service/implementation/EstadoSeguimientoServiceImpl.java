package org.gymanager.service.implementation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gymanager.model.domain.EstadoSeguimiento;
import org.gymanager.repository.specification.EstadoSeguimientoRepository;
import org.gymanager.service.specification.EstadoSeguimientoService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EstadoSeguimientoServiceImpl implements EstadoSeguimientoService {

    private static final String ESTADO_SEGUIMIENTO_NO_ENCONTRADO = "Estado seguimiento con id '%s' no encontrado";

    @NonNull
    private EstadoSeguimientoRepository estadoSeguimientoRepository;

    @Override
    public EstadoSeguimiento getEstadoSeguimientoById(Long idEstadoSeguimiento) {
        var estadoSeguimiento = estadoSeguimientoRepository.findById(idEstadoSeguimiento);

        if(estadoSeguimiento.isEmpty()){
            log.error(ESTADO_SEGUIMIENTO_NO_ENCONTRADO);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format(ESTADO_SEGUIMIENTO_NO_ENCONTRADO, idEstadoSeguimiento));
        }

        return estadoSeguimiento.get();
    }

    @Override
    public List<EstadoSeguimiento> getEstadoSeguimientos() {
        return estadoSeguimientoRepository.findAll();
    }
}
