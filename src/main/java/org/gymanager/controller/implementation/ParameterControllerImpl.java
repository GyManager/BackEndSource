package org.gymanager.controller.implementation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.gymanager.controller.specification.ParameterController;
import org.gymanager.model.client.EstadoSeguimientoDto;
import org.gymanager.service.specification.ParameterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ParameterControllerImpl implements ParameterController {

    @NonNull
    private ParameterService parameterService;

    @Override
    public ResponseEntity<List<String>> getTipoEjercicios() {
        return ResponseEntity.ok(parameterService.getTipoEjercicios());
    }

    @Override
    public ResponseEntity<List<String>> getObjetivos() {
        return ResponseEntity.ok(parameterService.getObjetivos());
    }

    @Override
    public ResponseEntity<List<String>> getSexos() {
        return ResponseEntity.ok(parameterService.getSexos());
    }

    @Override
    public ResponseEntity<List<String>> getTipoDocumentos() {
        return ResponseEntity.ok(parameterService.getTipoDocumentos());
    }

    @Override
    public ResponseEntity<List<String>> getBloques() {
        return ResponseEntity.ok(parameterService.getBloques());
    }
    
    @Override
    public ResponseEntity<List<EstadoSeguimientoDto>> getEstadoSeguimientos() {
        return ResponseEntity.ok(parameterService.getEstadoSeguimientos());
    }
}
