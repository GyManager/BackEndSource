package org.gymanager.service.implementation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gymanager.model.client.SeguimientoFinDiaDto;
import org.gymanager.model.domain.SeguimientoFinDia;
import org.gymanager.repository.specification.SeguimientoFinDiaRepository;
import org.gymanager.service.specification.EstadoSeguimientoService;
import org.gymanager.service.specification.PlanService;
import org.gymanager.service.specification.RutinaService;
import org.gymanager.service.specification.SeguimientoService;
import org.gymanager.service.specification.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class SeguimientoServiceImpl implements SeguimientoService {

    private static final String PLAN_NO_CORRESPONDE_AL_CLIENTE = "El plan no correspnode al cliente logeado";

    @NonNull
    private SeguimientoFinDiaRepository seguimientoFinDiaRepository;

    @NonNull
    private EstadoSeguimientoService estadoSeguimientoService;

    @NonNull
    private RutinaService rutinaService;

    @NonNull
    private PlanService planService;

    @NonNull
    private UsuarioService usuarioService;

    @Override
    public Long addSeguimientoEjercicio(Long idPlan, Long idMicroPlan, Long idRutina, SeguimientoFinDiaDto seguimientoFinDiaDto) {
        var plan = planService.getPlanEntityById(idPlan);

        if(Objects.isNull(plan)){
            throw new ResponseStatusException(HttpStatus.CONFLICT, PLAN_NO_CORRESPONDE_AL_CLIENTE);
        }

        usuarioService.validarIdClienteMatchUserFromRequest(plan.getCliente().getIdCliente());

        var rutina = rutinaService.getRutinaEntityById(idRutina);
        var estadoSeguimiento = estadoSeguimientoService.getEstadoSeguimientoById(seguimientoFinDiaDto.idEstadoSeguimiento());

        var seguimientoFinDia = new SeguimientoFinDia();
        seguimientoFinDia.setRutina(rutina);
        seguimientoFinDia.setFechaCarga(LocalDate.now());
        seguimientoFinDia.setObservacion(seguimientoFinDiaDto.observacion());
        seguimientoFinDia.setEstadoSeguimiento(estadoSeguimiento);

        return seguimientoFinDiaRepository.save(seguimientoFinDia).getIdSeguimientoFinDia();
    }
}
