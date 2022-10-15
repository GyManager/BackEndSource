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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "plan")
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPlan;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_usuario_profesor")
    private Usuario usuarioProfesor;

    @ManyToOne
    @JoinColumn(name = "id_objetivo")
    private Objetivo objetivo;

    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MicroPlan> microPlans;

    private LocalDateTime fechaDesde;
    private LocalDateTime fechaHasta;
    private LocalDateTime fechaEliminado;
    private String descripcion;

    public void setMicroPlans(List<MicroPlan> microPlans) {
        this.microPlans = microPlans;
        microPlans.forEach(microPlan -> microPlan.setPlan(this));
    }

    public void addAllMicroPlans(List<MicroPlan> microPlans) {
        microPlans.forEach(this::addMicroPlan);
    }

    public void addMicroPlan(MicroPlan microPlan){
        if(Objects.isNull(this.microPlans)){
            this.microPlans = new ArrayList<>();
        }
        this.microPlans.add(microPlan);
        microPlan.setPlan(this);
    }
}
