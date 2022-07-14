package org.gymanager.service.specification;

import org.gymanager.model.client.UsuarioDtoRegistro;
import org.gymanager.model.domain.Usuario;

import java.util.List;

public interface UsuarioService {

    Long addUsuario(UsuarioDtoRegistro usuarioDtoRegistro);

    List<Usuario> getUsuarios();

    Usuario getUsuarioById(Long idUsuario);

    void updateUsuarioById(Long idUsuario, UsuarioDtoRegistro usuarioDtoRegistro);

    void deleteUsuarioById(Long idUsuario);
}
