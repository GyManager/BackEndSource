package org.gymanager.service.specification;

import org.gymanager.model.client.notificacion.NotificacionDto;

import java.util.List;

public interface NotificacionService {
    List<NotificacionDto> getNotificaciones();
}
