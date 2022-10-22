package org.gymanager.model.client.notificacion;

import lombok.Getter;
import lombok.Setter;
import org.gymanager.model.enums.NotificacionID;

@Getter
@Setter
public class NotificacionDto {

    private NotificacionID id;
    private String mensaje;
    private String resumen;
    private Long valor;
    private Integer prioridad;
}
