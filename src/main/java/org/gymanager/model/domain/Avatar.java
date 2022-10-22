package org.gymanager.model.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "avatar")
public class Avatar {

    @Id
    private Long idUsuario;
    private String imagen;
}
