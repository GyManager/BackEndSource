package org.gymanager.repository.specification;

import org.gymanager.model.domain.MedidasCliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface MedidasClienteRepository extends JpaRepository<MedidasCliente, Long>{
    List<MedidasCliente> findAllByClienteIdCliente(Long idCliente);

    List<MedidasCliente> findAllByClienteIdClienteAndFecha(Long idCliente, LocalDate fecha);

    List<MedidasCliente> findAllByClienteIdClienteAndFechaGreaterThanEqual(Long idCliente, LocalDate fecha);
}
