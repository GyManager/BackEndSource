package org.gymanager.service.implementation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gymanager.converter.PlanEntityToDtoConverter;
import org.gymanager.model.client.PlanDto;
import org.gymanager.model.domain.Plan;
import org.gymanager.repository.specification.PlanRepository;
import org.gymanager.service.specification.PlanService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
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

    @Override
    public List<PlanDto> getPlansByClientId(Long idCliente) {
        return planEntityToDtoConverter.convert(planRepository.findByClienteIdCliente(idCliente));
    }

    @Override
    public PlanDto getPlanById(Long idPlan) {
        return planEntityToDtoConverter.convert(getPlanEntityById(idPlan));
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
}
