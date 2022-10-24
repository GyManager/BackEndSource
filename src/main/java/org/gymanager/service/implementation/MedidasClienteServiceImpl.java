package org.gymanager.service.implementation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gymanager.converter.MedidasClienteEntityToDtoConverter;
import org.gymanager.converter.MedidasClienteEntityToSmallDtoConverter;
import org.gymanager.model.client.MedidasClienteDto;
import org.gymanager.model.client.MedidasClienteSmallDto;
import org.gymanager.model.domain.MedidasCliente;
import org.gymanager.repository.specification.MedidasClienteRepository;
import org.gymanager.service.specification.ClienteService;
import org.gymanager.service.specification.MedidasClienteService;
import org.gymanager.service.specification.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MedidasClienteServiceImpl implements MedidasClienteService {

    private static final String MEDIDAS_CLIENTE_NO_ENCONTRADO = "Medidas cliente no encontrado";
    private static final String YA_EXISTE_MEDIDAS_CLIENTE_CON_FECHA_DE_HOY = """
            Ya existen medidas de este cliente cargadas en la fecha de hoy""";
    private static final String MEDIDAS_NO_CORRESPONDE_CON_CLIENTE = "Las medidas no corresponden con el cliente";

    @NonNull
    private MedidasClienteRepository medidasClienteRepository;

    @NonNull
    private MedidasClienteEntityToDtoConverter medidasClienteEntityToDtoConverter;

    @NonNull
    private MedidasClienteEntityToSmallDtoConverter medidasClienteEntityToSmallDtoConverter;

    @NonNull
    private ClienteService clienteService;

    @NonNull
    private UsuarioService usuarioService;

    @Override
    public List<MedidasClienteSmallDto> getMedidasByIdCliente(Long idCliente) {
        usuarioService.validarIdClienteMatchUserFromRequest(idCliente);
        return medidasClienteEntityToSmallDtoConverter.convert(medidasClienteRepository.findAllByClienteIdCliente(idCliente));
    }

    @Override
    public MedidasClienteDto getMedidasClienteById(Long idCliente, Long idMedidas){
        usuarioService.validarIdClienteMatchUserFromRequest(idCliente);

        var medidasCliente = getMedidasClienteEntityById(idMedidas);

        validarMedidasCorrespondeConCliente(medidasCliente, idCliente);

        return medidasClienteEntityToDtoConverter.convert(medidasCliente);
    }

    @Override
    public MedidasCliente getMedidasClienteEntityById(Long idMedidas){
        var medidasCliente = medidasClienteRepository.findById(idMedidas);

        if(medidasCliente.isEmpty()){
            log.error(MEDIDAS_CLIENTE_NO_ENCONTRADO);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, MEDIDAS_CLIENTE_NO_ENCONTRADO);
        }

        return medidasCliente.get();
    }

    @Override
    public Long addMedidas(Long idCliente, MedidasClienteDto medidasClienteDto) {
        usuarioService.validarIdClienteMatchUserFromRequest(idCliente);

        var cliente = clienteService.getClienteEntityById(idCliente);

        var existeMedidaClienteHoy = !medidasClienteRepository.findAllByClienteIdClienteAndFecha(
                idCliente,
                LocalDate.now())
                .isEmpty();

        if(existeMedidaClienteHoy){
            log.error(YA_EXISTE_MEDIDAS_CLIENTE_CON_FECHA_DE_HOY);
            throw new ResponseStatusException(HttpStatus.CONFLICT, YA_EXISTE_MEDIDAS_CLIENTE_CON_FECHA_DE_HOY);
        }

        var medidasCliente = new MedidasCliente();
        medidasCliente.setFecha(LocalDate.now());
        medidasCliente.setCliente(cliente);
        medidasCliente.setPeso(medidasClienteDto.peso());
        medidasCliente.setAltura(medidasClienteDto.altura());
        medidasCliente.setCervical(medidasClienteDto.cervical());
        medidasCliente.setDorsal(medidasClienteDto.dorsal());
        medidasCliente.setLumbar(medidasClienteDto.lumbar());
        medidasCliente.setCoxalPelvica(medidasClienteDto.coxalPelvica());
        medidasCliente.setCadera(medidasClienteDto.cadera());
        medidasCliente.setMuslosIzq(medidasClienteDto.muslosIzq());
        medidasCliente.setMuslosDer(medidasClienteDto.muslosDer());
        medidasCliente.setGemelosIzq(medidasClienteDto.gemelosIzq());
        medidasCliente.setGemelosDer(medidasClienteDto.gemelosDer());
        medidasCliente.setBrazoIzq(medidasClienteDto.brazoIzq());
        medidasCliente.setBrazoDer(medidasClienteDto.brazoDer());
        medidasCliente.setFoto(medidasClienteDto.foto());

        return medidasClienteRepository.save(medidasCliente).getIdMedidas();
    }

    @Override
    public void updateMedidasById(Long idCliente, Long idMedidas, MedidasClienteDto medidasClienteDto) {
        usuarioService.validarIdClienteMatchUserFromRequest(idCliente);

        var medidasCliente = getMedidasClienteEntityById(idMedidas);

        validarMedidasCorrespondeConCliente(medidasCliente, idCliente);

        medidasCliente.setPeso(medidasClienteDto.peso());
        medidasCliente.setAltura(medidasClienteDto.altura());
        medidasCliente.setCervical(medidasClienteDto.cervical());
        medidasCliente.setDorsal(medidasClienteDto.dorsal());
        medidasCliente.setLumbar(medidasClienteDto.lumbar());
        medidasCliente.setCoxalPelvica(medidasClienteDto.coxalPelvica());
        medidasCliente.setCadera(medidasClienteDto.cadera());
        medidasCliente.setMuslosIzq(medidasClienteDto.muslosIzq());
        medidasCliente.setMuslosDer(medidasClienteDto.muslosDer());
        medidasCliente.setGemelosIzq(medidasClienteDto.gemelosIzq());
        medidasCliente.setGemelosDer(medidasClienteDto.gemelosDer());
        medidasCliente.setBrazoIzq(medidasClienteDto.brazoIzq());
        medidasCliente.setBrazoDer(medidasClienteDto.brazoDer());
        medidasCliente.setFoto(medidasClienteDto.foto());

        medidasClienteRepository.save(medidasCliente);
    }

    @Override
    public void deleteMedidasById(Long idCliente, Long idMedidas) {
        usuarioService.validarIdClienteMatchUserFromRequest(idCliente);

        var medidasCliente = getMedidasClienteEntityById(idMedidas);

        validarMedidasCorrespondeConCliente(medidasCliente, idCliente);

        medidasClienteRepository.delete(medidasCliente);
    }

    private void validarMedidasCorrespondeConCliente(MedidasCliente medidasCliente, Long idCliente){
        if(!medidasCliente.getCliente().getIdCliente().equals(idCliente)){
            log.error(MEDIDAS_NO_CORRESPONDE_CON_CLIENTE);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, MEDIDAS_NO_CORRESPONDE_CON_CLIENTE);
        }
    }
}
