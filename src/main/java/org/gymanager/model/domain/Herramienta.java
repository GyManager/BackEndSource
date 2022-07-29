package org.gymanager.model.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "herramienta")
public class Herramienta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idHerramienta;

    private String nombre;

    private String descripcion;

    @ManyToMany
    @JoinTable( name = "ejercicio_por_herramienta",
            joinColumns = @JoinColumn(name = "id_herramienta"),
            inverseJoinColumns = @JoinColumn(name = "id_ejercicio"))
    private List<Ejercicio> ejercicios;
}
