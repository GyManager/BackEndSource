package org.gymanager.controller.implementation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.gymanager.controller.specification.MatriculaController;
import org.gymanager.model.client.MatriculaDto;
import org.gymanager.service.specification.MatriculaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MatriculaControllerImpl implements MatriculaController {

    @NonNull
    private MatriculaService matriculaService;

    @Override
    public ResponseEntity<List<MatriculaDto>> getMatriculasByIdCliente(Long idCliente, Boolean last) {
        return ResponseEntity.ok(matriculaService.getMatriculasByIdCliente(idCliente, last));
    }
}
