package org.gymanager.service.implementation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gymanager.model.domain.Objetivo;
import org.gymanager.repository.specification.ObjetivoRepository;
import org.gymanager.service.specification.ObjetivoService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ObjetivoServiceImpl implements ObjetivoService {

    private static final String OBJETIVO_NO_ENCONTRADO = "El objetivo '%s' no fue encontrado";

    @NonNull
    private ObjetivoRepository objetivoRepository;

    @Override
    public Objetivo getObjetivoByObjetivo(String objetivo) {
        Optional<Objetivo> objetivoOptional = objetivoRepository.findByObjetivo(objetivo);

        if(objetivoOptional.isEmpty()){
            log.error(OBJETIVO_NO_ENCONTRADO);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format(OBJETIVO_NO_ENCONTRADO, objetivo));
        }

        return objetivoOptional.get();
    }
}
