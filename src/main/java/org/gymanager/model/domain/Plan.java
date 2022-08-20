package org.gymanager.model.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.List;

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

    @OneToMany(mappedBy = "plan")
    private List<MicroPlan> microPlans;

    private LocalDate fechaDesde;
    private LocalDate fechaHasta;
    private String descripcion;
}
