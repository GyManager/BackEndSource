package org.gymanager.service.specification;

import org.gymanager.model.client.usuarios.UsuarioDto;

import java.util.List;

public interface UsuarioService {

    List<UsuarioDto> getUsuarios();
}
