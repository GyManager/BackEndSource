package org.gymanager.service.implementation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.gymanager.converter.UsuarioEntityToDtoConverter;
import org.gymanager.model.client.usuarios.UsuarioDto;
import org.gymanager.model.client.usuarios.UsuarioDtoRegistro;
import org.gymanager.model.domain.usuarios.Usuario;
import org.gymanager.repository.specification.UsuarioRepository;
import org.gymanager.service.specification.UsuarioService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    @NonNull
    private UsuarioRepository usuarioRepository;

    @NonNull
    private final PasswordEncoder passwordEncoder;

    @NonNull
    private UsuarioEntityToDtoConverter usuarioEntityToDtoConverter;

    @Override
    public UsuarioDto addUsuario(UsuarioDtoRegistro usuarioDtoRegistro) {
        Usuario usuario = new Usuario();
        usuario.setNombre(usuarioDtoRegistro.getNombre());
        usuario.setPass(passwordEncoder.encode(usuarioDtoRegistro.getPass()));
        usuario.setFechaAlta(LocalDate.now());
        usuario.setMail(usuarioDtoRegistro.getMail());

        return usuarioEntityToDtoConverter.convert(usuarioRepository.save(usuario));
    }

    @Override
    public List<UsuarioDto> getUsuarios() {
        return usuarioEntityToDtoConverter.convert(usuarioRepository.findAll());
    }
}
