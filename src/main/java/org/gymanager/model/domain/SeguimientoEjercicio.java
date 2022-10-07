package org.gymanager.model.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "seguimiento_ejercicio")
public class SeguimientoEjercicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSeguimientoEjercicio;

    private LocalDate fechaCarga;

    @ManyToOne
    @JoinColumn(name = "id_plan")
    private Plan plan;

    @ManyToOne
    @JoinColumn(name = "id_ejercicio_aplicado")
    private EjercicioAplicado ejercicioAplicado;

    private Integer cargaReal;
    private String tiempoReal;
    private String observacion;
}
