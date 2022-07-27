package org.gymanager.controller.implementation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.gymanager.controller.specification.HerramientaController;
import org.gymanager.model.client.HerramientaDto;
import org.gymanager.service.specification.HerramientaService;
import org.gymanager.service.specification.PasoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class HerramientasControllerImpl implements HerramientaController {

    @NonNull
    private HerramientaService herramientaService;

    @Override
    public ResponseEntity<List<HerramientaDto>> getHerramientasByIdEjercicio(Long idEjercicio) {
        return ResponseEntity.ok(herramientaService.getHerramientasByIdEjercicio(idEjercicio));
    }
}
