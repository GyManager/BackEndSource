package org.gymanager.service.specification;

import org.gymanager.model.client.usuarios.UsuarioDto;
import org.gymanager.model.client.usuarios.UsuarioDtoRegistro;

import java.util.List;

public interface UsuarioService {

    UsuarioDto addUsuario(UsuarioDtoRegistro usuarioDtoRegistro);

    List<UsuarioDto> getUsuarios();

    UsuarioDto getUsuarioById(Long idUsuario);
}
