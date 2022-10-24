package org.gymanager.service.implementation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gymanager.model.client.notificacion.NotificacionDto;
import org.gymanager.model.domain.Usuario;
import org.gymanager.model.enums.NotificacionID;
import org.gymanager.model.enums.PlanesFilter;
import org.gymanager.service.specification.MatriculaService;
import org.gymanager.service.specification.NotificacionService;
import org.gymanager.service.specification.PlanService;
import org.gymanager.service.specification.UsuarioService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificacionServiceImpl implements NotificacionService {

    @NonNull
    private PlanService planService;

    @NonNull
    private UsuarioService usuarioService;

    @Override
    public List<NotificacionDto> getNotificaciones() {
        var notificaciones = new ArrayList<NotificacionDto>();
        var usuario = usuarioService.getUsuarioEntityFromCurrentToken();

        var solicitudesDeFeedbackPlanes = getSolicitudesDeFeedbackPlanes(usuario);
        if(Objects.nonNull(solicitudesDeFeedbackPlanes)){
            notificaciones.add(solicitudesDeFeedbackPlanes);
        }

        return notificaciones;
    }

    public NotificacionDto getSolicitudesDeFeedbackPlanes(Usuario usuario){
        if(!usuario.esClienteActivo()) {
            return null;
        }

        var countPlanesVencidos = planService.getPlansEntitiesByIdCliente(usuario.getCliente().getIdCliente(), PlanesFilter.VENCIDOS)
                .stream()
                .filter(plan -> Objects.isNull(plan.getEstadoSeguimiento()))
                .count();

        if(countPlanesVencidos == 0L) {
            return null;
        }

        var notificacion = new NotificacionDto();
        notificacion.setMensaje("Nos interesa tu opinion sobre los planes que completaste");
        notificacion.setValor(countPlanesVencidos);
        notificacion.setId(NotificacionID.FEEDBACK_PLANES);

        return notificacion;
    }
}
