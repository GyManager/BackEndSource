package org.gymanager.model.domain;

import lombok.Getter;
import lombok.Setter;
import org.gymanager.model.enums.ClienteEstado;
import org.gymanager.model.enums.MatriculaEstado;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.Where;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCliente;

    @OneToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_objetivo", nullable = false)
    private Objetivo objetivo;

    private String direccion;

    @Column(nullable = false)
    private Date fechaNacimiento;
    private String observaciones;

    @OneToMany
    @JoinColumn(updatable = false, insertable = false, referencedColumnName = "idCliente", name = "id_cliente")
    @Where(clause = "CURRENT_TIMESTAMP BETWEEN fecha_inicio AND fecha_vencimiento")
    private List<Matricula> matriculasActivas;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Matricula> matriculas;

    public ClienteEstado getClienteEstado() {
        if(usuario.getRoles().stream().noneMatch(rol -> rol.getNombreRol().equals("CLIENTE"))){
           return ClienteEstado.DESACTIVADO;
        }
        var matriculaActiva = matriculasActivas.stream().findFirst();
        if(matriculaActiva.isEmpty()){
            return ClienteEstado.NO_MATRICULADO;
        }
        if(matriculaActiva.get().getMatriculaEstado().equals(MatriculaEstado.ACTIVA)) {
            return ClienteEstado.MATRICULADO;
        }

        var proximaMatricula = matriculas.stream()
                .filter(matricula -> matricula.getFechaInicio().isAfter(matriculaActiva.get().getFechaVencimiento()))
                .min(Comparator.comparing(Matricula::getFechaInicio));
        if(proximaMatricula.isPresent()){
            return ClienteEstado.MATRICULADO;
        }
        return ClienteEstado.PRONTO_A_VENCER;
    }
}
