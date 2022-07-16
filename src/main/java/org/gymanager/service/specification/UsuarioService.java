package org.gymanager.service.specification;

import org.gymanager.model.client.UsuarioDto;
import org.gymanager.model.domain.Usuario;

import java.util.List;

public interface UsuarioService {

    List<UsuarioDto> getUsuarios();

    UsuarioDto getUsuarioById(Long idUsuario);

    Usuario getUsuarioEntityById(Long idUsuario);

    Long addUsuario(UsuarioDto usuarioDto);

    void updateUsuarioById(Long idUsuario, UsuarioDto usuarioDto);

    void deleteUsuarioById(Long idUsuario);
}
