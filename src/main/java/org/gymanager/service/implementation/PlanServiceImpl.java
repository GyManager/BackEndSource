package org.gymanager.service.implementation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gymanager.converter.PlanEntityToDtoConverter;
import org.gymanager.converter.PlanEntityToDtoDetailsConverter;
import org.gymanager.model.client.PlanDto;
import org.gymanager.model.client.PlanDtoDetails;
import org.gymanager.model.domain.Plan;
import org.gymanager.repository.specification.PlanRepository;
import org.gymanager.service.specification.ClienteService;
import org.gymanager.service.specification.MicroPlanService;
import org.gymanager.service.specification.ObjetivoService;
import org.gymanager.service.specification.PlanService;
import org.gymanager.service.specification.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlanServiceImpl implements PlanService {

    private static final String PLAN_NO_ENCONTRADO = "Plan no encontrado";

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
    public List<PlanDto> getPlansByClientId(Long idCliente) {
        return planEntityToDtoConverter.convert(planRepository.findByClienteIdCliente(idCliente));
    }

    @Override
    @Transactional
    public PlanDto getPlanById(Long idPlan) {
        return planEntityToDtoDetailsConverter.convert(getPlanEntityById(idPlan));
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

        var microPlanes = microPlanService.crearMicroPlanes(planDtoDetails.getMicroPlans());
        var objetivo = objetivoService.getObjetivoByObjetivo(planDtoDetails.getObjetivo());
        var usuario = Objects.nonNull(planDtoDetails.getIdUsuarioProfesor()) ?
                usuarioService.getUsuarioEntityById(planDtoDetails.getIdUsuarioProfesor()) :
                usuarioService.getUsuarioEntityByMail(planDtoDetails.getUsuarioProfesor());
        var cliente = clienteService.getClienteEntityById(idCliente);

        plan.setDescripcion(planDtoDetails.getDescripcion().trim());
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

        microPlanService.actualizarMicroPlanesPlan(planDtoDetails.getMicroPlans(), plan);
        var objetivo = objetivoService.getObjetivoByObjetivo(planDtoDetails.getObjetivo());
        var cliente = clienteService.getClienteEntityById(idCliente);

        plan.setDescripcion(planDtoDetails.getDescripcion().trim());
        plan.setFechaDesde(planDtoDetails.getFechaDesde());
        plan.setFechaHasta(planDtoDetails.getFechaHasta());
        plan.setObjetivo(objetivo);
        plan.setCliente(cliente);

        planRepository.save(plan);
    }

    @Override
    public void deletePlanById(Long idCliente, Long idPlan) {
        var plan = getPlanEntityById(idPlan);

        planRepository.delete(plan);
    }
}
