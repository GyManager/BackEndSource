package org.gymanager.model.domain;

import lombok.Getter;
import lombok.Setter;
import org.gymanager.model.enums.MatriculaEstado;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.Period;

import static org.gymanager.model.enums.MatriculaEstado.ACTIVA;
import static org.gymanager.model.enums.MatriculaEstado.NO_INICIADA;
import static org.gymanager.model.enums.MatriculaEstado.PRONTO_A_VENCER;
import static org.gymanager.model.enums.MatriculaEstado.VENCIDA;

@Entity
@Getter
@Setter
@Table(name = "matricula")
public class Matricula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMatricula;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    private LocalDate fechaPago;
    private LocalDate fechaInicio;
    private LocalDate fechaVencimiento;
    private Integer cantidadMeses;
    private Integer cantidadDiasSemana;

    public MatriculaEstado getMatriculaEstado() {
        var now = LocalDate.now();
        if(fechaInicio.isAfter(now)) {
            return NO_INICIADA;
        } else if(fechaVencimiento.isBefore(now)) {
            return VENCIDA;
        } else if(Period.between(now, fechaVencimiento).getDays() < 7) {
            return PRONTO_A_VENCER;
        } else {
            return ACTIVA;
        }
    }
}
