package org.gymanager.controller.specification;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.gymanager.model.client.notificacion.NotificacionDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/api/notificaciones")
@Tag(name = "Notificaciones", description = "Notificaciones")
public interface NotificacionController {

    @Operation(summary = "Obtener las notificaciones de mi usuario",
            description = "Esta operación es para obtener las notificaciones de mi usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")
    })
    @GetMapping(produces = { "application/json"})
    ResponseEntity<List<NotificacionDto>> getNotificaciones();
}
