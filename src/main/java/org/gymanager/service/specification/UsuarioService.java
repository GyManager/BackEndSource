package org.gymanager.service.specification;

import org.gymanager.model.client.UsuarioDto;
import org.gymanager.model.client.UsuarioDtoDetails;
import org.gymanager.model.domain.Usuario;

import java.util.List;

public interface UsuarioService {

    List<UsuarioDto> getUsuarios();

    UsuarioDtoDetails getUsuarioById(Long idUsuario);

    Usuario getUsuarioEntityById(Long idUsuario);

    Long addUsuario(UsuarioDtoDetails UsuarioDtoDetails);

    Long addUsuario(UsuarioDto usuarioDto, List<String> roles);

    void updateUsuarioById(Long idUsuario, UsuarioDtoDetails usuarioDtoDetails);

    void updateUsuarioById(Long idUsuario, UsuarioDto usuarioDto, List<String> roles, Boolean updateRoles);

    void deleteUsuarioById(Long idUsuario);

    Usuario getUsuarioEntityByMail(String mail);
}
