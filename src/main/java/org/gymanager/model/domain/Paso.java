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
@Table(name = "paso")
public class Paso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPaso;

    private Integer numeroPaso;

    private String contenido;

    private String imagen;

    @ManyToOne
    @JoinColumn(name = "id_ejercicio", nullable = false)
    private Ejercicio ejercicio;
}
