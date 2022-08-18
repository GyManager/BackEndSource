package org.gymanager.service.implementation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gymanager.model.domain.Bloque;
import org.gymanager.model.domain.Sexo;
import org.gymanager.repository.specification.BloqueRepository;
import org.gymanager.repository.specification.SexoRepository;
import org.gymanager.service.specification.BloqueService;
import org.gymanager.service.specification.SexoService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BloqueServiceImpl implements BloqueService {

    private static final String BLOQUE_NO_ENCONTRADO = "El bloque '%s' no fue encontrado";

    @NonNull
    private BloqueRepository bloqueRepository;

    @Override
    public Bloque getBloqueByNombre(String nombre) {
        Optional<Bloque> bloque = bloqueRepository.findByNombre(nombre);

        if(bloque.isEmpty()){
            log.error(String.format(BLOQUE_NO_ENCONTRADO, nombre));
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format(BLOQUE_NO_ENCONTRADO, nombre));
        }

        return bloque.get();
    }

    @Override
    public List<Bloque> getBloques() {
        return bloqueRepository.findAll();
    }
}
