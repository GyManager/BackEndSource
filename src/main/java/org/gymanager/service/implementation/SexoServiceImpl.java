package org.gymanager.service.implementation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gymanager.model.domain.Sexo;
import org.gymanager.repository.specification.SexoRepository;
import org.gymanager.service.specification.SexoService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SexoServiceImpl implements SexoService {

    private static final String NOMBRE_SEXO_NO_ENCONTRADO = "Nombre del sexo '%s' no encontrado";

    @NonNull
    private SexoRepository sexoRepository;

    @Override
    public Sexo getSexoByNombreSexo(String nombreSexo) {
        Optional<Sexo> sexo = sexoRepository.findBySexo(nombreSexo);

        if(sexo.isEmpty()){
            log.error(NOMBRE_SEXO_NO_ENCONTRADO);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format(NOMBRE_SEXO_NO_ENCONTRADO, nombreSexo));
        }

        return sexo.get();
    }
}
