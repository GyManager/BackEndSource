package org.gymanager.model.client.usuarios.token;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class UsuarioToken {

    private String username;
    private List<String> authorities;
}
