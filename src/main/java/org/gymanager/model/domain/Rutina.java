package org.gymanager.model.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "rutina")
public class Rutina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRutina;

    @ManyToOne
    @JoinColumn(name = "id_micro_plan")
    private MicroPlan microPlan;

    private String nombre;
    private Boolean esTemplate;

    @OneToMany(mappedBy = "rutina", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EjercicioAplicado> ejercicioAplicados;

    public void setEjercicioAplicados(List<EjercicioAplicado> ejercicioAplicados) {
        this.ejercicioAplicados = ejercicioAplicados;
        ejercicioAplicados.forEach(ejercicioAplicado -> ejercicioAplicado.setRutina(this));
    }

    public void addAllEjercicioAplicados(List<EjercicioAplicado> ejercicioAplicados) {
        ejercicioAplicados.forEach(this::addEjercicioAplicado);
    }

    public void addEjercicioAplicado(EjercicioAplicado ejercicioAplicado){
        if(Objects.isNull(this.ejercicioAplicados)){
            this.ejercicioAplicados = new ArrayList<>();
        }
        this.ejercicioAplicados.add(ejercicioAplicado);
        ejercicioAplicado.setRutina(this);
    }
}
