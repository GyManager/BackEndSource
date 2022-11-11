package org.gymanager.controller.implementation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.gymanager.controller.specification.NotificacionController;
import org.gymanager.model.client.notificacion.NotificacionDto;
import org.gymanager.service.specification.NotificacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class NotificacionControllerImpl implements NotificacionController {

    @NonNull
    private NotificacionService notificacionService;

    @Override
    public ResponseEntity<List<NotificacionDto>> getNotificaciones() {
        return ResponseEntity.ok(notificacionService.getNotificaciones());
    }
}
