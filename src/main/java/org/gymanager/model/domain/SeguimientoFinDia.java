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
@Table(name = "seguimiento_fin_dia")
public class SeguimientoFinDia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSeguimientoFinDia;

    @ManyToOne
    @JoinColumn(name = "id_rutina")
    private Rutina rutina;

    private LocalDate fechaCarga;
    private String observacion;

    @ManyToOne
    @JoinColumn(name = "id_estado_seguimiento")
    private EstadoSeguimiento estadoSeguimiento;
}
