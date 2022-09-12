package org.gymanager.service.specification;

import org.gymanager.model.client.UsuarioDto;
import org.gymanager.model.client.UsuarioDtoDetails;
import org.gymanager.model.domain.Usuario;
import org.gymanager.model.enums.UsuarioSortBy;
import org.gymanager.model.page.GyManagerPage;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface UsuarioService {

    GyManagerPage<UsuarioDto> getUsuarios(String search, Integer page, Integer pageSize, UsuarioSortBy sortBy, Sort.Direction direction);

    UsuarioDtoDetails getUsuarioById(Long idUsuario);

    Usuario getUsuarioEntityById(Long idUsuario);

    Long addUsuario(UsuarioDtoDetails UsuarioDtoDetails);

    Long addUsuario(UsuarioDto usuarioDto, List<String> roles);

    void updateUsuarioById(Long idUsuario, UsuarioDtoDetails usuarioDtoDetails);

    void updateUsuarioById(Long idUsuario, UsuarioDto usuarioDto, List<String> roles, Boolean updateRoles);

    void deleteUsuarioById(Long idUsuario);

    Usuario getUsuarioEntityByMail(String mail);
}
