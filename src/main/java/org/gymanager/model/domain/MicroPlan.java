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
@Table(name = "micro_plan")
public class MicroPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMicroPlan;

    @ManyToOne
    @JoinColumn(name = "id_plan")
    private Plan plan;

    private String nombre;
    private Boolean esTemplate;
    private Integer numeroOrden;

    @OneToMany(mappedBy = "microPlan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Observacion> observaciones;

    @OneToMany(mappedBy = "microPlan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rutina> rutinas;

    public void setRutinas(List<Rutina> rutinas) {
        this.rutinas = rutinas;
        rutinas.forEach(rutina -> rutina.setMicroPlan(this));
    }

    public void addAllRutinas(List<Rutina> rutinas) {
        rutinas.forEach(this::addRutina);
    }

    public void addRutina(Rutina rutina){
        if(Objects.isNull(this.rutinas)){
            this.rutinas = new ArrayList<>();
        }
        this.rutinas.add(rutina);
        rutina.setMicroPlan(this);
    }
}
