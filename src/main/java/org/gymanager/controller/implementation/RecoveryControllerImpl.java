package org.gymanager.controller.implementation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.gymanager.controller.specification.RecoveryController;
import org.gymanager.handler.specification.RecoveryServiceHandler;
import org.gymanager.model.client.UsuarioRecovery;
import org.gymanager.service.specification.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RecoveryControllerImpl implements RecoveryController {

    @NonNull
    private RecoveryServiceHandler recoveryServiceHandler;

    @Override
    public ResponseEntity<Void> resetPasswordUsuarioByMail(UsuarioRecovery usuarioRecovery) {
        recoveryServiceHandler.resetPasswordUsuarioByMail(usuarioRecovery);
        return ResponseEntity.noContent().build();
    }
}
