package org.gymanager.service.specification;

import org.gymanager.model.client.UsuarioDto;
import org.gymanager.model.client.UsuarioDtoRegistro;
import org.gymanager.model.domain.Usuario;

import java.util.List;

public interface UsuarioService {

    List<UsuarioDto> getUsuarios();

    UsuarioDto getUsuarioById(Long idUsuario);

    Usuario getUsuarioEntityById(Long idUsuario);

    Long addUsuario(UsuarioDtoRegistro usuarioDtoRegistro);

    void updateUsuarioById(Long idUsuario, UsuarioDtoRegistro usuarioDtoRegistro);

    void deleteUsuarioById(Long idUsuario);
}
