package org.gymanager.service.specification;

import org.gymanager.model.client.MedidasClienteDto;
import org.gymanager.model.domain.MedidasCliente;
import org.gymanager.model.enums.MedidasClienteFilter;

import java.util.List;

public interface MedidasClienteService {

    List<MedidasClienteDto> getMedidasByIdCliente(Long idCliente, MedidasClienteFilter medidasClienteFilter);

    MedidasCliente getMedidasClienteEntityById(Long idMedidas);

    Long addMedidas(Long idCliente, MedidasClienteDto medidasClienteDto);

    void updateMedidasById(Long idMedidas, MedidasClienteDto medidasClienteDto);

    void deleteMedidasById(Long idCliente, Long idMedidas);
}
