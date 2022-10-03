package org.gymanager.controller.implementation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.gymanager.controller.specification.MedidasClienteController;
import org.gymanager.model.client.MedidasClienteDto;
import org.gymanager.model.enums.MedidasClienteFilter;
import org.gymanager.service.specification.MedidasClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MedidasClienteControllerImpl implements MedidasClienteController {

    @NonNull
    private MedidasClienteService medidasClienteService;

    @Override
    public ResponseEntity<List<MedidasClienteDto>> getMedidasByIdCliente(Long idCliente, MedidasClienteFilter medidasClienteFilter) {
        return ResponseEntity.ok(medidasClienteService.getMedidasByIdCliente(idCliente, medidasClienteFilter));
    }

    @Override
    public ResponseEntity<Long> addMedidas(Long idCliente, MedidasClienteDto medidasClienteDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(medidasClienteService.addMedidas(idCliente, medidasClienteDto));
    }

    @Override
    public ResponseEntity<Void> updateMedidasById(Long idMedidas, MedidasClienteDto medidasClienteDto) {
        medidasClienteService.updateMedidasById(idMedidas, medidasClienteDto);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> deleteMedidasById(Long idCliente, Long idMedidas) {
        medidasClienteService.deleteMedidasById(idCliente, idMedidas);
        return ResponseEntity.noContent().build();
    }
}
