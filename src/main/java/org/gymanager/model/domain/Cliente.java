package org.gymanager.model.domain;

import lombok.Getter;
import lombok.Setter;
import org.gymanager.model.enums.ClienteEstado;
import org.hibernate.annotations.Formula;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCliente;

    @OneToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_objetivo", nullable = false)
    private Objetivo objetivo;

    private String direccion;

    @Column(nullable = false)
    private Date fechaNacimiento;
    private String observaciones;

    @Formula(value = """
            (SELECT DATE_PART('day', m.fecha_vencimiento::TIMESTAMP - CURRENT_TIMESTAMP) FROM {h-schema}matricula m
            WHERE m.id_cliente = id_cliente AND CURRENT_TIMESTAMP BETWEEN m.fecha_inicio AND m.fecha_vencimiento)""")
    private Integer diasHastaVencimientoMatricula;

    public ClienteEstado getClienteEstado() {
        return ClienteEstado.getEstadoSegunVencimiento(diasHastaVencimientoMatricula);
    }
}
