package org.gymanager.model.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    @OneToOne(mappedBy = "usuario")
    private Cliente cliente;

    @Column(nullable = false)
    private Long numeroDocumento;

    @ManyToOne
    @JoinColumn(name = "id_tipo_documento", nullable = false)
    private TipoDocumento tipoDocumento;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    @ManyToOne
    @JoinColumn(name = "id_sexo")
    private Sexo sexo;

    @Column(nullable = false)
    private String pass;
    private LocalDateTime fechaAlta;
    private LocalDateTime fechaBaja;

    @Column(unique = true, nullable = false)
    private String mail;
    private Long celular;

    @ManyToMany
    @JoinTable( name = "usuario_por_rol",
            joinColumns = @JoinColumn(name = "id_usuario"),
            inverseJoinColumns = @JoinColumn(name = "id_rol"))
    private List<Rol> roles;

    public Boolean esClienteActivo(){
        return Objects.nonNull(this.cliente)
                && this.roles.stream().map(Rol::getNombreRol).anyMatch(nombre -> nombre.equals("CLIENTE"));
    }
}
