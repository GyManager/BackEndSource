package org.gymanager.service.implementation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gymanager.converter.SeguimientoEjercicioEntityToDtoConverter;
import org.gymanager.model.client.SeguimientoEjercicioRequestDto;
import org.gymanager.model.domain.SeguimientoEjercicio;
import org.gymanager.repository.specification.SeguimientoEjercicioRepository;
import org.gymanager.service.specification.EjercicioAplicadoService;
import org.gymanager.service.specification.SeguimientoEjercicioService;
import org.gymanager.service.specification.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
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

        var seguimientoEjercicio =new SeguimientoEjercicio();
        seguimientoEjercicio.setFechaCarga(LocalDate.now());
        seguimientoEjercicio.setPlan(plan);
        seguimientoEjercicio.setEjercicioAplicado(ejercicioAplicado);
        seguimientoEjercicio.setCargaReal(seguimientoEjercicioRequestDto.cargaReal());
        seguimientoEjercicio.setTiempoReal(seguimientoEjercicioRequestDto.tiempoReal());
        seguimientoEjercicio.setObservacion(seguimientoEjercicioRequestDto.observacion());

        return seguimientoEjercicioRepository.save(seguimientoEjercicio).getIdSeguimientoEjercicio();
    }
}
