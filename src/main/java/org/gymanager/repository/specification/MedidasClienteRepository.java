package org.gymanager.repository.specification;

import org.gymanager.model.domain.MedidasCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MedidasClienteRepository extends JpaRepository<MedidasCliente, Long>{
    List<MedidasCliente> findAllByClienteIdCliente(Long idCliente);

    @Query(nativeQuery = true, value = """
            SELECT * FROM medidas_cliente WHERE id_cliente = :idCliente \
            AND fecha = (SELECT MAX(fecha) FROM medidas_cliente mc WHERE mc.id_cliente = :idCliente)""")
    List<MedidasCliente> findLastByClienteIdCliente(Long idCliente);
}
