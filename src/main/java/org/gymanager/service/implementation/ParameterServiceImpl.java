package org.gymanager.service.implementation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gymanager.model.domain.Objetivo;
import org.gymanager.model.domain.Sexo;
import org.gymanager.model.domain.TipoDocumento;
import org.gymanager.model.domain.TipoEjercicio;
import org.gymanager.service.specification.ObjetivoService;
import org.gymanager.service.specification.ParameterService;
import org.gymanager.service.specification.SexoService;
import org.gymanager.service.specification.TipoDocumentoService;
import org.gymanager.service.specification.TipoEjercicioService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ParameterServiceImpl implements ParameterService {

    @NonNull
    private TipoEjercicioService tipoEjercicioService;

    @NonNull
    private ObjetivoService objetivoService;

    @NonNull
    private SexoService sexoService;

    @NonNull
    private TipoDocumentoService tipoDocumentoService;


    @Override
    public List<String> getTipoEjercicios() {
        return tipoEjercicioService.getTipoEjercicios().stream().map(TipoEjercicio::getNombre).toList();
    }

    @Override
    public List<String> getObjetivos() {
        return objetivoService.getObjetivos().stream().map(Objetivo::getObjetivo).toList();
    }

    @Override
    public List<String> getSexos() {
        return sexoService.getSexos().stream().map(Sexo::getSexo).toList();
    }

    @Override
    public List<String> getTipoDocumentos() {
        return tipoDocumentoService.getTipoDocumentos().stream().map(TipoDocumento::getTipo).toList();
    }
}
