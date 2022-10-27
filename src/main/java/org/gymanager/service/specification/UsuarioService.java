package org.gymanager.service.specification;

import org.gymanager.model.client.UsuarioDto;
import org.gymanager.model.client.UsuarioDtoDetails;
import org.gymanager.model.client.UsuarioInfoDto;
import org.gymanager.model.client.UsuarioPasswordDto;
import org.gymanager.model.domain.Usuario;
import org.gymanager.model.enums.UsuarioSortBy;
import org.gymanager.model.page.GyManagerPage;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;

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

    void validarIdClienteMatchUserFromRequest(Long idCliente);

    Usuario getUsuarioEntityFromCurrentToken();

    void removeRolUsuarioById(Long idUsuario, List<String> roles);

    void addRolUsuarioById(Long idUsuario, List<String> roles);

    UsuarioInfoDto getUsuarioInfoByMail(String mail);

    void updatePasswordUsuarioById(Long idUsuario, String mailFromToken, UsuarioPasswordDto usuarioPasswordDto);

    void resetPasswordUsuarioById(Long idUsuario);
}
