package org.gymanager.service.implementation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gymanager.converter.SeguimientoEjercicioEntityToDtoConverter;
import org.gymanager.model.client.EjercicioDto;
import org.gymanager.model.client.SeguimientoEjercicioDto;
import org.gymanager.model.client.SeguimientoEjercicioRequestDto;
import org.gymanager.model.domain.SeguimientoEjercicio;
import org.gymanager.model.enums.SeguimientosFilter;
import org.gymanager.repository.specification.SeguimientoEjercicioRepository;
import org.gymanager.service.specification.EjercicioAplicadoService;
import org.gymanager.service.specification.EjercicioService;
import org.gymanager.service.specification.PlanService;
import org.gymanager.service.specification.SeguimientoEjercicioService;
import org.gymanager.service.specification.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class SeguimientoEjercicioServiceImpl implements SeguimientoEjercicioService {

    private static final String PLAN_NO_CORRESPONDE_AL_CLIENTE = "El plan no correspnode al cliente logeado";

    @NonNull
    private SeguimientoEjercicioRepository seguimientoEjercicioRepository;

    @NonNull
    private SeguimientoEjercicioEntityToDtoConverter seguimientoEjercicioEntityToDtoConverter;

    @NonNull
    private EjercicioAplicadoService ejercicioAplicadoService;

    @NonNull
    private EjercicioService ejercicioService;

    @NonNull
    private PlanService planService;

    @NonNull
    private UsuarioService usuarioService;


    @Override
    public Long addSeguimientoEjercicio(Long idPlan, Long idEjercicioAplicado,
                                        SeguimientoEjercicioRequestDto seguimientoEjercicioRequestDto) {
        var ejercicioAplicado = ejercicioAplicadoService.getEjercicioAplicadoEntityById(idEjercicioAplicado);

        var plan = ejercicioAplicado.getRutina().getMicroPlan().getPlan();
        if(Objects.isNull(plan)){
            throw new ResponseStatusException(HttpStatus.CONFLICT, PLAN_NO_CORRESPONDE_AL_CLIENTE);
        }

        usuarioService.validarIdClienteMatchUserFromRequest(plan.getCliente().getIdCliente());

        var seguimientosEjercicioHoy = seguimientoEjercicioRepository.findAllByEjercicioAplicadoIdEjercicioAplicadoAndFechaCarga(idEjercicioAplicado, LocalDate.now());

        var seguimientoEjercicio = seguimientosEjercicioHoy.stream().findFirst().orElse(new SeguimientoEjercicio());
        seguimientoEjercicio.setFechaCarga(LocalDate.now());
        seguimientoEjercicio.setPlan(plan);
        seguimientoEjercicio.setEjercicioAplicado(ejercicioAplicado);
        seguimientoEjercicio.setCargaReal(seguimientoEjercicioRequestDto.cargaReal());
        seguimientoEjercicio.setTiempoReal(seguimientoEjercicioRequestDto.tiempoReal());
        seguimientoEjercicio.setObservacion(seguimientoEjercicioRequestDto.observacion());

        return seguimientoEjercicioRepository.save(seguimientoEjercicio).getIdSeguimientoEjercicio();
    }

    @Override
    public List<SeguimientoEjercicioDto> getSeguimientoEjercicioByIdRutina(Long idPlan,
                                                                           Long idMicroPlan,
                                                                           Long idRutina,
                                                                           SeguimientosFilter seguimientosFilter) {

        var plan = planService.getPlanEntityById(idPlan);
        usuarioService.validarIdClienteMatchUserFromRequest(plan.getCliente().getIdCliente());

        var seguimientos = switch (seguimientosFilter){
            case HOY -> seguimientoEjercicioRepository.findAllByEjercicioAplicadoRutinaIdRutinaAndFechaCarga(idRutina, LocalDate.now());
            case TODAS -> seguimientoEjercicioRepository.findAllByEjercicioAplicadoRutinaIdRutina(idRutina);
            case ESTA_SEMANA -> throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED,
                    "Esta operacion no esta implementada para este servicio");
        };

        return seguimientos.stream()
                .map(seguimientoEjercicioEntityToDtoConverter::convert)
                .filter(Objects::nonNull)
                .toList();
    }

    @Override
    public List<EjercicioDto> getSeguimientoEjercicios(Long idCliente) {

        usuarioService.validarIdClienteMatchUserFromRequest(idCliente);
        var idEjerciciosConSeguimiento = seguimientoEjercicioRepository
                .findDistinctIdEjercicioByHasSeguimientoWithIdCliente(idCliente);
        return ejercicioService.getEjerciciosByIdIn(idEjerciciosConSeguimiento);
    }

    @Override
    public List<SeguimientoEjercicioDto> getSeguimientoEjercicioByIdEjercicio(Long idCliente, Long idEjercicio) {

        usuarioService.validarIdClienteMatchUserFromRequest(idCliente);
        return seguimientoEjercicioEntityToDtoConverter.convert(seguimientoEjercicioRepository
                .findAllByPlanClienteIdClienteAndEjercicioAplicadoEjercicioIdEjercicio(idCliente, idEjercicio));
    }
}
