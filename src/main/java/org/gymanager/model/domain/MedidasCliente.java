package org.gymanager.model.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "medidas_cliente")
public class MedidasCliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMedidas;

    @OneToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    @Column(nullable = false)
    private Date fecha;
    private Float peso;
    private Float altura;
    private Float cervical;
    private Float dorsal;
    private Float lumbar;
    private Float coxalPelvica;
    private Float cadera;
    private Float muslosIzq;
    private Float muslosDer;
    private Float rodillasIzq;
    private Float rodillasDer;
    private Float gemelosIzq;
    private Float gemelosDer;
    private Float brazoIzq;
    private Float brazoDer;
    private String foto;
}
