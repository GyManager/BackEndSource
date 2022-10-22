package org.gymanager.service.implementation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gymanager.converter.PlanEntityToDtoConverter;
import org.gymanager.converter.PlanEntityToDtoDetailsConverter;
import org.gymanager.model.client.ClientePlanResumenDto;
import org.gymanager.model.client.PlanDto;
import org.gymanager.model.client.PlanDtoDetails;
import org.gymanager.model.domain.EstadoSeguimiento;
import org.gymanager.model.domain.Matricula;
import org.gymanager.model.domain.Plan;
import org.gymanager.model.enums.PlanesFilter;
import org.gymanager.repository.specification.PlanRepository;
import org.gymanager.service.specification.ClienteService;
import org.gymanager.service.specification.MicroPlanService;
import org.gymanager.service.specification.ObjetivoService;
import org.gymanager.service.specification.PlanService;
import org.gymanager.service.specification.UsuarioService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.time.LocalDateTime.now;
import static java.time.temporal.ChronoUnit.DAYS;
import static org.apache.commons.lang3.BooleanUtils.isTrue;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlanServiceImpl implements PlanService {

    private static final String PLAN_NO_ENCONTRADO = "Plan no encontrado";
    private static final String FECHA_HASTA_INVALIDA = """
            La fecha de fin / fecha hasta del plan no puede ser anterior a la fecha de hoy""";
    private static final String FECHA_HASTA_ACTUALIZADA_INVALIDA = """
            Si actualiza la fecha de fin / fecha hasta del plan, la misma no puede ser anterior a la fecha de hoy""";
    private static final String YA_EXISTE_UN_PLAN_EN_ESE_ESTADO_PARA_EL_CLIENTE = """
            El cliente elegido ya tiene un plan %s""";

    @Value("${logical-delete}")
    private Boolean logicalDelete;

    @NonNull
    private PlanRepository planRepository;

    @NonNull
    private PlanEntityToDtoConverter planEntityToDtoConverter;

    @NonNull
    private PlanEntityToDtoDetailsConverter planEntityToDtoDetailsConverter;

    @NonNull
    private MicroPlanService microPlanService;

    @NonNull
    private ObjetivoService objetivoService;

    @NonNull
    private ClienteService clienteService;

    @NonNull
    private UsuarioService usuarioService;

    @Override
    public List<PlanDto> getPlansByIdCliente(Long idCliente, PlanesFilter planesFilter, LocalDate fechaDesde, Boolean validateUser) {
        if(validateUser){
            usuarioService.validarIdClienteMatchUserFromRequest(idCliente);
        }
        return planEntityToDtoConverter.convert(getPlansEntitiesByIdCliente(idCliente, planesFilter, fechaDesde));
    }

    @Override
    public List<Plan> getPlansEntitiesByIdCliente(Long idCliente, PlanesFilter planesFilter) {
        return getPlansEntitiesByIdCliente(idCliente, planesFilter, null);
    }

    @Override
    public List<Plan> getPlansEntitiesByIdCliente(Long idCliente, PlanesFilter planesFilter, LocalDate fechaDesde) {
        var now = now();
        var planes = switch (planesFilter) {
            case TODOS -> planRepository.findByClienteIdCliente(idCliente);
            case ACTIVOS -> planRepository.findAllByClienteIdClienteAndFechaHastaGreaterThanAndFechaDesdeLessThanEqualAndFechaEliminadoNull(idCliente, now, now);
            case VENCIDOS -> planRepository.findAllByClienteIdClienteAndFechaHastaLessThanEqualAndFechaEliminadoNull(idCliente, now);
            case FUTUROS -> planRepository.findAllByClienteIdClienteAndFechaDesdeAfterAndFechaEliminadoNull(idCliente, now);
            case ELIMINADOS -> planRepository.findAllByClienteIdClienteAndFechaEliminadoNotNull(idCliente);
        };
        if(Objects.isNull(fechaDesde)){
            return planes;
        }
        return planes.stream().filter(plan -> plan.getFechaHasta().toLocalDate().isAfter(fechaDesde)).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PlanDto getPlanById(Long idPlan, Boolean validateUser) {
        var plan = getPlanEntityById(idPlan);
        if(validateUser){
            usuarioService.validarIdClienteMatchUserFromRequest(plan.getCliente().getIdCliente());
        }
        return planEntityToDtoDetailsConverter.convert(plan);
    }

    @Override
    public Plan getPlanEntityById(Long idPlan){
        Optional<Plan> plan = planRepository.findById(idPlan);

        if(plan.isEmpty()){
            log.error(PLAN_NO_ENCONTRADO);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, PLAN_NO_ENCONTRADO);
        }

        return plan.get();
    }

    @Override
    @Transactional
    public Long addPlan(Long idCliente, PlanDtoDetails planDtoDetails) {
        var plan = new Plan();

        validarFechaNoPasada(planDtoDetails.getFechaHasta(), FECHA_HASTA_INVALIDA);

        actualizarOtrosPlanes(idCliente, planDtoDetails, Boolean.FALSE);

        var microPlanes = microPlanService.crearMicroPlanes(planDtoDetails.getMicroPlans());
        var objetivo = objetivoService.getObjetivoByObjetivo(planDtoDetails.getObjetivo());
        var usuario = Objects.nonNull(planDtoDetails.getIdUsuarioProfesor()) ?
                usuarioService.getUsuarioEntityById(planDtoDetails.getIdUsuarioProfesor()) :
                usuarioService.getUsuarioEntityByMail(planDtoDetails.getUsuarioProfesor());
        var cliente = clienteService.getClienteEntityById(idCliente);

        plan.setDescripcion(planDtoDetails.getDescripcion());
        plan.setFechaDesde(planDtoDetails.getFechaDesde());
        plan.setFechaHasta(planDtoDetails.getFechaHasta());
        plan.setObjetivo(objetivo);
        plan.setCliente(cliente);
        plan.setUsuarioProfesor(usuario);
        plan.setMicroPlans(microPlanes);

        return planRepository.save(plan).getIdPlan();
    }

    @Override
    @Transactional
    public void updatePlanById(Long idCliente, Long idPlan, PlanDtoDetails planDtoDetails) {
        var plan = getPlanEntityById(idPlan);

        actualizarOtrosPlanes(idCliente, planDtoDetails, Boolean.TRUE);

        if(!planDtoDetails.getFechaHasta().isEqual(plan.getFechaHasta())){
            validarFechaNoPasada(planDtoDetails.getFechaHasta(), FECHA_HASTA_ACTUALIZADA_INVALIDA);
            plan.setFechaHasta(planDtoDetails.getFechaHasta());
        }
        microPlanService.actualizarMicroPlanesPlan(planDtoDetails.getMicroPlans(), plan);
        var objetivo = objetivoService.getObjetivoByObjetivo(planDtoDetails.getObjetivo());
        var cliente = clienteService.getClienteEntityById(idCliente);

        plan.setDescripcion(planDtoDetails.getDescripcion());
        plan.setFechaDesde(planDtoDetails.getFechaDesde());
        plan.setObjetivo(objetivo);
        plan.setCliente(cliente);

        planRepository.save(plan);
    }

    @Override
    public void deletePlanById(Long idCliente, Long idPlan) {
        var plan = getPlanEntityById(idPlan);

        if(isTrue(logicalDelete)){
            plan.setFechaEliminado(LocalDateTime.now());
            planRepository.save(plan);
        } else {
            planRepository.delete(plan);
        }
    }

    @Override
    public ClientePlanResumenDto getResumenPlanesClienteById(Long idCliente) {
        var cliente = clienteService.getClienteEntityById(idCliente);
        var planVigente = getPlansEntitiesByIdCliente(idCliente, PlanesFilter.ACTIVOS)
                .stream().findFirst();
        var matriculaVigente = cliente.getMatriculasActivas().stream().findFirst();

        return new ClientePlanResumenDto(
                cliente.getIdCliente(),
                Objects.isNull(cliente.getObjetivo()) ? null : cliente.getObjetivo().getObjetivo(),
                cliente.getObservaciones(),
                planVigente.map(Plan::getFechaHasta).orElse(null),
                planVigente.map(Plan::getIdPlan).orElse(null),
                matriculaVigente.map(Matricula::getCantidadDiasSemana).orElse(null)
        );
    }

    @Override
    public void updatePlanSeguimientoById(Plan plan, String observacion, EstadoSeguimiento estadoSeguimiento){
        plan.setObservacionCliente(observacion);
        plan.setEstadoSeguimiento(estadoSeguimiento);
        planRepository.save(plan);
    }

    private void validarFechaNoPasada(LocalDateTime fecha, String error){
        if(fecha.isBefore(now())){
            log.error(error);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, error);
        }
    }

    private void actualizarOtrosPlanes(Long idCliente, PlanDtoDetails planDtoDetails, Boolean esActualizacion){
        var planesActivos = getPlansEntitiesByIdCliente(idCliente, PlanesFilter.ACTIVOS)
                .stream().filter(plan -> !plan.getIdPlan().equals(planDtoDetails.getIdPlan())).toList();
        var planesFuturos = getPlansEntitiesByIdCliente(idCliente, PlanesFilter.FUTUROS)
                .stream().filter(plan -> !plan.getIdPlan().equals(planDtoDetails.getIdPlan())).toList();

        var now = now();
        if(!planesActivos.isEmpty() && !esActualizacion &&
                (planDtoDetails.getFechaDesde().isBefore(now) || planDtoDetails.getFechaDesde().isEqual(now))){
            log.error(String.format(YA_EXISTE_UN_PLAN_EN_ESE_ESTADO_PARA_EL_CLIENTE, "activo"));
            throw new ResponseStatusException(HttpStatus.CONFLICT, String.format(YA_EXISTE_UN_PLAN_EN_ESE_ESTADO_PARA_EL_CLIENTE, "activo"));
        }
        if(!planesFuturos.isEmpty() && planDtoDetails.getFechaDesde().isAfter(now)){
            log.error(String.format(YA_EXISTE_UN_PLAN_EN_ESE_ESTADO_PARA_EL_CLIENTE, "futuro"));
            throw new ResponseStatusException(HttpStatus.CONFLICT, String.format(YA_EXISTE_UN_PLAN_EN_ESE_ESTADO_PARA_EL_CLIENTE, "futuro"));
        }

        planesActivos.stream()
                .filter(plan -> plan.getFechaHasta().isAfter(planDtoDetails.getFechaDesde()))
                .forEach(plan -> plan.setFechaHasta(planDtoDetails.getFechaDesde()));

        planesFuturos.stream()
                .filter(plan -> plan.getFechaDesde().isBefore(planDtoDetails.getFechaHasta()))
                .forEach(plan -> {
                    var pushAmount = DAYS.between(plan.getFechaDesde().toLocalDate(),
                            planDtoDetails.getFechaHasta().toLocalDate());
                    plan.setFechaHasta(plan.getFechaHasta().plusDays(pushAmount));
                    plan.setFechaDesde(planDtoDetails.getFechaHasta());
                });
    }
}
