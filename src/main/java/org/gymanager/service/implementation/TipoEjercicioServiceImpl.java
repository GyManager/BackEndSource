package org.gymanager.service.implementation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gymanager.model.domain.TipoEjercicio;
import org.gymanager.repository.specification.TipoEjercicioRepository;
import org.gymanager.service.specification.TipoEjercicioService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TipoEjercicioServiceImpl implements TipoEjercicioService {

    private static final String TIPO_EJERCICIO_NO_ENCONTRADO = "Tipo ejercicio '%s' no encontrado";

    @NonNull
    private TipoEjercicioRepository tipoEjercicioRepository;

    @Override
    public TipoEjercicio getTipoEjercicioByNombre(String nombre) {
        Optional<TipoEjercicio> tipoEjercicio = tipoEjercicioRepository.findByNombre(nombre);

        if(tipoEjercicio.isEmpty()){
            log.error(TIPO_EJERCICIO_NO_ENCONTRADO);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format(TIPO_EJERCICIO_NO_ENCONTRADO, nombre));
        }

        return tipoEjercicio.get();
    }
}
