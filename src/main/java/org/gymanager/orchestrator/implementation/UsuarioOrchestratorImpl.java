package org.gymanager.orchestrator.implementation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gymanager.converter.UsuarioEntityToDtoConverter;
import org.gymanager.model.client.UsuarioDto;
import org.gymanager.model.client.UsuarioDtoRegistro;
import org.gymanager.orchestrator.specification.UsuarioOrchestrator;
import org.gymanager.service.specification.UsuarioService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsuarioOrchestratorImpl implements UsuarioOrchestrator {

    @NonNull
    private UsuarioEntityToDtoConverter usuarioEntityToDtoConverter;

    @NonNull
    private UsuarioService usuarioService;

    @Override
    public Long addUsuario(UsuarioDtoRegistro usuarioDtoRegistro) {
        return usuarioService.addUsuario(usuarioDtoRegistro);
    }

    @Override
    public List<UsuarioDto> getUsuarios() {
        return usuarioService.getUsuarios()
                .stream()
                .map(usuarioEntityToDtoConverter::convert)
                .collect(Collectors.toList());
    }

    @Override
    public UsuarioDto getUsuarioById(Long idUsuario) {
        return usuarioEntityToDtoConverter.convert(usuarioService.getUsuarioById(idUsuario));
    }

    @Override
    public void updateUsuarioById(Long idUsuario, UsuarioDtoRegistro usuarioDtoRegistro) {
        usuarioService.updateUsuarioById(idUsuario, usuarioDtoRegistro);
    }

    @Override
    public void deleteUsuarioById(Long idUsuario) {
        usuarioService.deleteUsuarioById(idUsuario);
    }
}
