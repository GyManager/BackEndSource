package org.gymanager.model.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "observacion_micro_plan")
public class Observacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_observacion_micro_plan")
    private Long idObservacion;

    @ManyToOne
    @JoinColumn(name = "id_micro_plan")
    private MicroPlan microPlan;

    private Integer numeroSemana;
    private String observacion;
}
