package org.gymanager.model.domain.clientes;

import lombok.Getter;
import lombok.Setter;
import org.gymanager.model.domain.usuarios.Usuario;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "cliente", uniqueConstraints={@UniqueConstraint(columnNames = {"numero_documento", "id_tipo_documento"})})
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCliente;

    @OneToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @Column(name = "numero_documento")
    private Long numeroDocumento;

    @ManyToOne
    @JoinColumn(name = "id_tipo_documento", nullable = false)
    private TipoDocumento tipoDocumento;

    private String nombre;
    private String apellido;
    private String direccion;
    private Date fechaNacimiento;
    private String celular;
    private String objetivo;
}
