package org.gymanager.model.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigInteger;

@Getter
@Setter
@Entity
@Table(name = "cliente_estado_count")
public class CountClienteEstado {
    @Id
    private String clienteEstado;
    private BigInteger cantidad;
}
