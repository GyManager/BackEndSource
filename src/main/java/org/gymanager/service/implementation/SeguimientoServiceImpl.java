package org.gymanager.service.implementation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gymanager.converter.SeguimientoFinDiaEntityToDtoConverter;
import org.gymanager.model.client.SeguimientoFinDiaDto;
import org.gymanager.model.client.SeguimientoFinDiaDtoDetail;
import org.gymanager.model.client.SeguimientoPlanDto;
import org.gymanager.model.domain.CountFeedbackFinDia;
import org.gymanager.model.domain.EstadoSeguimiento;
import org.gymanager.model.domain.SeguimientoFinDia;
import org.gymanager.model.enums.SeguimientosFilter;
import org.gymanager.repository.specification.SeguimientoFinDiaRepository;
import org.gymanager.service.specification.EstadoSeguimientoService;
import org.gymanager.service.specification.PlanService;
import org.gymanager.service.specification.RutinaService;
import org.gymanager.service.specification.SeguimientoService;
import org.gymanager.service.specification.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class SeguimientoServiceImpl implements SeguimientoService {

    private static final String PLAN_NO_CORRESPONDE_AL_CLIENTE = "El plan no correspnode al cliente logeado";

    @NonNull
    private SeguimientoFinDiaRepository seguimientoFinDiaRepository;

    @NonNull
    private SeguimientoFinDiaEntityToDtoConverter seguimientoFinDiaEntityToDtoConverter;

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

    @Override
    public List<SeguimientoFinDiaDtoDetail> getSeguimientoFinDiaByIdMicroPlan(Long idPlan, Long idMicroPlan, SeguimientosFilter seguimientosFilter) {
        var plan = planService.getPlanEntityById(idPlan);
        usuarioService.validarIdClienteMatchUserFromRequest(plan.getCliente().getIdCliente());

        var now = LocalDate.now();
        var seguimientos = switch (seguimientosFilter){
            case HOY -> seguimientoFinDiaRepository.findAllByRutinaMicroPlanIdMicroPlanAndFechaCarga(idMicroPlan, LocalDate.now());
            case TODAS -> seguimientoFinDiaRepository.findAllByRutinaMicroPlanIdMicroPlan(idMicroPlan);
            case ESTA_SEMANA -> seguimientoFinDiaRepository.findAllByRutinaMicroPlanIdMicroPlanAndFechaCargaGreaterThanEqualAndFechaCargaLessThan(
                    idMicroPlan,
                    now.with(TemporalAdjusters.previous(DayOfWeek.MONDAY)),
                    now.with(TemporalAdjusters.next(DayOfWeek.MONDAY))
            );
        };

        return seguimientos.stream()
                .map(seguimientoFinDiaEntityToDtoConverter::convert)
                .filter(Objects::nonNull)
                .toList();
    }

    @Override
    public void addSeguimientoPlan(Long idPlan, SeguimientoPlanDto seguimientoPlanDto) {
        var plan = planService.getPlanEntityById(idPlan);

        if(Objects.isNull(plan)){
            throw new ResponseStatusException(HttpStatus.CONFLICT, PLAN_NO_CORRESPONDE_AL_CLIENTE);
        }

        usuarioService.validarIdClienteMatchUserFromRequest(plan.getCliente().getIdCliente());

        var estadoSeguimiento = estadoSeguimientoService.getEstadoSeguimientoById(seguimientoPlanDto.idEstadoSeguimiento());

        planService.updatePlanSeguimientoById(plan, seguimientoPlanDto.observacion(), estadoSeguimiento);
    }

    @Override
    public List<Long> getIdClientesCountSeguimientoFinDiaByEstado(Long dayCount, EstadoSeguimiento estadoSeguimiento){
        return seguimientoFinDiaRepository.findCountByIdEstadoSeguimientoAndFechaNotOlderThanDays(
                dayCount,
                estadoSeguimiento.getIdEstadoSeguimiento()
        );
    }

    @Override
    public List<CountFeedbackFinDia> getCountByFechaNotOlderThanDays(Long dayCount){
        return seguimientoFinDiaRepository.findCountByFechaNotOlderThanDays(dayCount.doubleValue());
    }
}
