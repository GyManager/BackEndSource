package org.gymanager.service.implementation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gymanager.converter.RutinaEntityToDtoConverter;
import org.gymanager.converter.RutinaEntityToDtoDetailsConverter;
import org.gymanager.model.client.RutinaDto;
import org.gymanager.model.client.RutinaDtoDetails;
import org.gymanager.model.domain.MicroPlan;
import org.gymanager.model.domain.Rutina;
import org.gymanager.repository.specification.RutinaRepository;
import org.gymanager.service.specification.EjercicioAplicadoService;
import org.gymanager.service.specification.RutinaService;
import org.gymanager.service.specification.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RutinaServiceImpl implements RutinaService {

    private static final String RUTINA_NO_ENCONTRADO = "Rutina no encontrada";

    @NonNull
    private RutinaRepository rutinaRepository;

    @NonNull
    private RutinaEntityToDtoConverter rutinaEntityToDtoConverter;

    @NonNull
    private RutinaEntityToDtoDetailsConverter rutinaEntityToDtoDetailsConverter;

    @NonNull
    private EjercicioAplicadoService ejercicioAplicadoService;

    @NonNull
    private UsuarioService usuarioService;

    @Override
    public List<RutinaDto> getRutinasByIdMicroPlan(Long idMicroPlan) {
        return rutinaEntityToDtoConverter.convert(rutinaRepository.findByMicroPlanIdMicroPlan(idMicroPlan));
    }

    @Override
    public Rutina getRutinaEntityById(Long idRutina){
        var rutina = rutinaRepository.findById(idRutina);

        if(rutina.isEmpty()){
            log.error(RUTINA_NO_ENCONTRADO);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, RUTINA_NO_ENCONTRADO);
        }

        return rutina.get();
    }

    @Override
    public List<Rutina> crearRutinas(List<RutinaDtoDetails> rutinas) {
        return rutinas.stream().map(this::crearRutina).collect(Collectors.toList());
    }

    @Override
    public void actualizarRutinasMicroPlan(List<RutinaDtoDetails> rutinaDtoDetails, MicroPlan microPlan) {

        final var mapRutinaExistenteIdRutinaActualizadaDto = rutinaDtoDetails.stream()
                .filter(rutinaDto -> Objects.nonNull(rutinaDto.getIdRutina()))
                .collect(Collectors.toMap(RutinaDtoDetails::getIdRutina, Function.identity()));

        microPlan.getRutinas().removeIf(rutina -> !mapRutinaExistenteIdRutinaActualizadaDto.containsKey(rutina.getIdRutina()));

        microPlan.getRutinas().forEach(rutina -> {
            var rutinaDtoActualizado = mapRutinaExistenteIdRutinaActualizadaDto.get(rutina.getIdRutina());

            ejercicioAplicadoService.actualizarEjerciciosAplicadosRutina(rutinaDtoActualizado.getEjerciciosAplicados(), rutina);

            rutina.setNombre(rutinaDtoActualizado.getNombre());
            rutina.setEsTemplate(Boolean.TRUE.equals(rutinaDtoActualizado.getEsTemplate()));
        });

        microPlan.addAllRutinas(crearRutinas(getRutinasSinId(rutinaDtoDetails)));
    }

    @Override
    public RutinaDtoDetails getRutinasByIdRutinaAndIdMicroPlan(Long idMicroPlan, Long idRutina, Boolean validateUser) {
        var rutina = getRutinaEntityById(idRutina);

        if(validateUser){
            var idCliente = Objects.isNull(rutina.getMicroPlan().getPlan()) ? null :
                    rutina.getMicroPlan().getPlan().getCliente().getIdCliente();
            usuarioService.validarIdClienteMatchUserFromRequest(idCliente);
        }

        return rutinaEntityToDtoDetailsConverter.convert(rutina);
    }

    private List<RutinaDtoDetails> getRutinasSinId(List<RutinaDtoDetails> rutinaDtoDetails){
        return rutinaDtoDetails.stream()
                .filter(rutinaDto -> Objects.isNull(rutinaDto.getIdRutina()))
                .toList();
    }

    private Rutina crearRutina(RutinaDtoDetails rutinaDtoDetails) {
        var rutina = new Rutina();

        var ejerciciosAplicados = ejercicioAplicadoService.crearEjerciciosAplicados(rutinaDtoDetails.getEjerciciosAplicados());

        rutina.setNombre(rutinaDtoDetails.getNombre());
        rutina.setEsTemplate(Boolean.TRUE.equals(rutinaDtoDetails.getEsTemplate()));
        rutina.setEjercicioAplicados(ejerciciosAplicados);

        return rutina;
    }
}
