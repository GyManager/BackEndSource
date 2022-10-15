package org.gymanager.repository.specification;

import org.gymanager.model.domain.Matricula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface MatriculaRepository extends JpaRepository<Matricula, Long> {

    List<Matricula> findAllByClienteIdCliente(Long idCliente);

    List<Matricula> findAllByClienteIdClienteAndFechaVencimientoAfter(Long idCliente, LocalDateTime date);

    @Query(nativeQuery = true, value = """
            SELECT m1.* FROM {h-schema}matricula m1
            WHERE m1.id_cliente = :idCliente
            AND CURRENT_TIMESTAMP BETWEEN m1.fecha_inicio AND m1.fecha_vencimiento""")
    List<Matricula> findAllByClienteIdClienteAndCurrent(Long idCliente);
}
