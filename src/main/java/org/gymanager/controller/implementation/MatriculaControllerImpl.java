package org.gymanager.controller.implementation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.gymanager.controller.specification.MatriculaController;
import org.gymanager.model.client.MatriculaDto;
import org.gymanager.model.enums.MatriculasFilter;
import org.gymanager.service.specification.MatriculaService;
import org.gymanager.utils.Permisos;
import org.gymanager.utils.UserPermissionValidation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MatriculaControllerImpl implements MatriculaController {

    @NonNull
    private MatriculaService matriculaService;

    @Override
    public ResponseEntity<List<MatriculaDto>> getMatriculasByIdCliente(Long idCliente, MatriculasFilter matriculasFilter) {
        var validateUser = !UserPermissionValidation.userHasPermission(
                SecurityContextHolder.getContext().getAuthentication(),
                Permisos.GET_MATRICULAS);

        return ResponseEntity.ok(matriculaService.getMatriculasByIdCliente(idCliente, matriculasFilter, validateUser));
    }

    @Override
    public ResponseEntity<Long> addMatricula(Long idCliente, MatriculaDto matriculaDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(matriculaService.addMatricula(idCliente, matriculaDto));
    }

    @Override
    public ResponseEntity<Void> deleteMatriculaById(Long idCliente, Long idMatricula) {
        matriculaService.deleteMatriculaById(idCliente,idMatricula);
        return ResponseEntity.noContent().build();
    }
}
