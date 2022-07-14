package org.gymanager.orchestrator.specification;

import org.gymanager.model.client.UsuarioDto;
import org.gymanager.model.client.UsuarioDtoRegistro;

import java.util.List;

public interface UsuarioOrchestrator {

    Long addUsuario(UsuarioDtoRegistro usuarioDtoRegistro);

    List<UsuarioDto> getUsuarios();

    UsuarioDto getUsuarioById(Long idUsuario);

    void updateUsuarioById(Long idUsuario, UsuarioDtoRegistro usuarioDtoRegistro);

    void deleteUsuarioById(Long idUsuario);
}
