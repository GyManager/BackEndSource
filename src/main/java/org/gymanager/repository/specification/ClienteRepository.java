package org.gymanager.repository.specification;

import org.gymanager.model.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Long>, JpaSpecificationExecutor<Cliente> {

    @Query(nativeQuery = true, value = """
            select distinct c.id_cliente \
            from {h-schema}cliente c \
            join {h-schema}matricula m on m.id_cliente = c.id_cliente \
            where m.fecha_vencimiento - current_date <= :dayCount \
            and :dayOverdue <= m.fecha_vencimiento - current_date \
            and not exists (select id_matricula \
            	from {h-schema}matricula mfutura \
            	where mfutura.id_cliente = c.id_cliente \
            	and mfutura.fecha_inicio > current_date
            )""")
    List<Long> getIdClientesConMatriculaProximoVencimiento(Long dayCount, Long dayOverdue);
}
