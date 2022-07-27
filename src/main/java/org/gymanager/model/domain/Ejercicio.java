package org.gymanager.model.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "ejercicio")
public class Ejercicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEjercicio;

    @Column(nullable = false)
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "id_tipo_ejercicio", nullable = false)
    private TipoEjercicio tipoEjercicio;

    private String video;

    @ManyToMany
    @JoinTable( name = "ejercicio_por_herramienta",
            joinColumns = @JoinColumn(name = "id_ejercicio"),
            inverseJoinColumns = @JoinColumn(name = "id_herramienta"))
    private List<Herramienta> herramientas;

    @OneToMany(mappedBy = "ejercicio", cascade = CascadeType.ALL)
    private List<Paso> pasos;

    public void setPasos(List<Paso> pasos) {
        this.pasos = pasos;
        pasos.forEach(paso -> paso.setEjercicio(this));
    }
}
