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

@Entity
@Getter
@Setter
@Table(name = "ejercicio_aplicado")
public class EjercicioAplicado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEjercicioAplicado;

    @ManyToOne
    @JoinColumn(name = "id_rutina")
    private Rutina rutina;

    @ManyToOne
    @JoinColumn(name = "id_ejercicio")
    private Ejercicio ejercicio;

    @ManyToOne
    @JoinColumn(name = "id_bloque", nullable = false)
    private Bloque bloque;

    private Integer series;
    private Integer repeticiones;
    private String pausaMicro;
    private String pausaMacro;
    private String carga;
    private String tiempo;
    private Boolean esTemplate;
}
