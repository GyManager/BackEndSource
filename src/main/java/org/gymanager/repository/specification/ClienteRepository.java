package org.gymanager.repository.specification;

import org.gymanager.model.domain.Cliente;
import org.gymanager.model.domain.CountClienteEstado;
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

    @Query(nativeQuery = true, value = """
            select c.id_cliente \
            from {h-schema}cliente c \
            join {h-schema}plan p on p.id_cliente = c.id_cliente and p.fecha_hasta > current_date \
            left join {h-schema}micro_plan mp on mp.id_plan = p.id_plan \
            left join {h-schema}rutina r on r.id_micro_plan = mp.id_micro_plan \
            left join {h-schema}seguimiento_fin_dia sfd on sfd.id_rutina = r.id_rutina \
            left join {h-schema}matricula m on m.id_cliente = c.id_cliente \
            where current_date between m.fecha_inicio and m.fecha_vencimiento \
            group by c.id_cliente \
            having max(sfd.fecha_carga) is not null \
            and current_date - max(sfd.fecha_carga) >= :dayCount""")
    List<Long> getIdClientesSinFinalizarDia(Long dayCount);




    @Query(value = "select cec from CountClienteEstado cec")
    List<CountClienteEstado> getCountClientesByClienteEstado();
}
