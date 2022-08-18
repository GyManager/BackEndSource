package org.gymanager.service.implementation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gymanager.model.domain.TipoDocumento;
import org.gymanager.repository.specification.TipoDocumentoRepository;
import org.gymanager.service.specification.TipoDocumentoService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TipoDocumentoServiceImpl implements TipoDocumentoService {

    private static final String TIPO_DOCUMENTO_NO_ENCONTRADO = "Tipo documento '%s' no encontrado";

    @NonNull
    private TipoDocumentoRepository tipoDocumentoRepository;

    @Override
    public TipoDocumento getTipoDocumentoByTipo(String tipo) {
        Optional<TipoDocumento> tipoDocumento = tipoDocumentoRepository.findByTipo(tipo);

        if(tipoDocumento.isEmpty()){
            log.error(TIPO_DOCUMENTO_NO_ENCONTRADO);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format(TIPO_DOCUMENTO_NO_ENCONTRADO, tipo));
        }

        return tipoDocumento.get();
    }

    @Override
    public List<TipoDocumento> getTipoDocumentos() {
        return tipoDocumentoRepository.findAll();
    }
}
