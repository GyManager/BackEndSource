package org.gymanager.controller.implementation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.gymanager.controller.specification.RutinaController;
import org.gymanager.model.client.RutinaDto;
import org.gymanager.model.client.RutinaDtoDetails;
import org.gymanager.service.specification.RutinaService;
import org.gymanager.utils.Permisos;
import org.gymanager.utils.UserPermissionValidation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RutinaControllerImpl implements RutinaController {

    @NonNull
    private RutinaService rutinaService;

    @Override
    public ResponseEntity<List<RutinaDto>> getRutinasByIdMicroPlan(Long idMicroPlan) {
        return ResponseEntity.ok(rutinaService.getRutinasByIdMicroPlan(idMicroPlan));
    }

    @Override
    public ResponseEntity<RutinaDtoDetails> getRutinasByIdRutinaAndIdMicroPlan(Long idMicroPlan, Long idRutina) {
        var validateUser = !UserPermissionValidation.userHasPermission(
                SecurityContextHolder.getContext().getAuthentication(),
                Permisos.GET_MICRO_PLANES);

        return ResponseEntity.ok(rutinaService.getRutinasByIdRutinaAndIdMicroPlan(idMicroPlan, idRutina, validateUser));
    }
}
