package org.gymanager.repository.specification;

import org.gymanager.model.domain.Matricula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MatriculaRepository extends JpaRepository<Matricula, Long> {

    List<Matricula> findAllByClienteIdCliente(Long idCliente);

    @Query(nativeQuery = true, value = """
            SELECT m1.* FROM {h-schema}matricula m1 
            WHERE m1.id_cliente = :idCliente
            AND m1.fecha_pago = (SELECT MAX(m2.fecha_inicio) 
                FROM {h-schema}matricula m2 
                WHERE m2.id_cliente = :idCliente)""")
    List<Matricula> findAllByClienteIdClienteAndFechaInicioLast(Long idCliente);
}
