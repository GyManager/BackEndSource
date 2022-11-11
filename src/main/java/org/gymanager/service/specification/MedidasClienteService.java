package org.gymanager.service.specification;

import org.gymanager.model.client.MedidasClienteDto;
import org.gymanager.model.client.MedidasClienteSmallDto;
import org.gymanager.model.client.MedidasClienteSummaryDto;
import org.gymanager.model.domain.MedidasCliente;
import org.gymanager.model.enums.MedidasClienteFilter;
import org.gymanager.model.enums.MedidasTipo;

import java.time.LocalDate;
import java.util.List;

public interface MedidasClienteService {

    List<MedidasClienteSmallDto> getMedidasByIdCliente(Long idCliente);

    MedidasClienteDto getMedidasClienteById(Long idCliente, Long idMedidas);

    MedidasCliente getMedidasClienteEntityById(Long idMedidas);

    Long addMedidas(Long idCliente, MedidasClienteDto medidasClienteDto);

    void updateMedidasById(Long idCliente, Long idMedidas, MedidasClienteDto medidasClienteDto);

    void deleteMedidasById(Long idCliente, Long idMedidas);

    MedidasClienteSummaryDto getSummaryMedidaByIdClienteAndTipo(Long idCliente, MedidasTipo medidasTipo, LocalDate fechaInicial);
}
