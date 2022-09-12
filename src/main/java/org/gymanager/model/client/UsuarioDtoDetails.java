package org.gymanager.model.client;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UsuarioDtoDetails extends UsuarioDto{

    private List<String> roles;
}
