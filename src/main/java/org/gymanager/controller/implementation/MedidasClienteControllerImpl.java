package org.gymanager.controller.implementation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.gymanager.controller.specification.MedidasClienteController;
import org.gymanager.model.client.MedidasClienteDto;
import org.gymanager.model.client.MedidasClienteSmallDto;
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
    public ResponseEntity<List<MedidasClienteSmallDto>> getMedidasByIdCliente(Long idCliente) {
        return ResponseEntity.ok(medidasClienteService.getMedidasByIdCliente(idCliente));
    }

    @Override
    public ResponseEntity<MedidasClienteDto> getMedidasClienteById(Long idCliente, Long idMedidas) {
        return ResponseEntity.ok(medidasClienteService.getMedidasClienteById(idCliente, idMedidas));
    }

    @Override
    public ResponseEntity<Long> addMedidas(Long idCliente, MedidasClienteDto medidasClienteDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(medidasClienteService.addMedidas(idCliente, medidasClienteDto));
    }

    @Override
    public ResponseEntity<Void> updateMedidasById(Long idCliente, Long idMedidas, MedidasClienteDto medidasClienteDto) {
        medidasClienteService.updateMedidasById(idCliente, idMedidas, medidasClienteDto);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> deleteMedidasById(Long idCliente, Long idMedidas) {
        medidasClienteService.deleteMedidasById(idCliente, idMedidas);
        return ResponseEntity.noContent().build();
    }
}
