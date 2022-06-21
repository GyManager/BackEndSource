package org.gymanager.service.implementation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gymanager.converter.UsuarioEntityToDtoConverter;
import org.gymanager.model.client.usuarios.UsuarioDto;
import org.gymanager.model.client.usuarios.UsuarioDtoRegistro;
import org.gymanager.model.domain.usuarios.Permiso;
import org.gymanager.model.domain.usuarios.Rol;
import org.gymanager.model.domain.usuarios.Usuario;
import org.gymanager.repository.specification.UsuarioRepository;
import org.gymanager.service.specification.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsuarioServiceImpl implements UsuarioService, UserDetailsService {

    private static final String USUARIO_CON_MAIL_NO_ENCONTRADO = "Usuario con mail (%s) no encontrado";
    private static final String USUARIO_NO_ENCONTRADO = "Usuario no encontrado";
    private static final String USUARIO_YA_EXISTE = "Ya existe un usuario registrado con el mail (%s)";
    private static final String MAIL_VACIO = "El mail de login no debe ser vacio";

    @NonNull
    private UsuarioRepository usuarioRepository;

    @NonNull
    private final PasswordEncoder passwordEncoder;

    @NonNull
    private UsuarioEntityToDtoConverter usuarioEntityToDtoConverter;

    /**
     * Implementacion del metodo de UserDetailsService para validar las credenciales de login
     */
    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        if(Objects.isNull(mail)){
            log.error(String.format(MAIL_VACIO));
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(MAIL_VACIO));
        }

        Optional<Usuario> usuario = usuarioRepository.findByMail(mail);

        if(usuario.isEmpty()){
            log.error(String.format(USUARIO_CON_MAIL_NO_ENCONTRADO, mail));
            throw new UsernameNotFoundException(String.format(USUARIO_CON_MAIL_NO_ENCONTRADO, mail));
        }

        Collection<SimpleGrantedAuthority> authorities = usuario.get().getRoles().stream()
                .map(Rol::getPermisos)
                .flatMap(List::stream)
                .map(Permiso::getNombrePermiso)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return new User(usuario.get().getMail(),
                usuario.get().getPass(),
                authorities);
    }

    @Override
    public UsuarioDto addUsuario(UsuarioDtoRegistro usuarioDtoRegistro) {
        validarUsuarioConMailNoExiste(usuarioDtoRegistro.getMail());

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

    @Override
    public UsuarioDto getUsuarioById(Long idUsuario) {
        Optional<Usuario> usuario = usuarioRepository.findById(idUsuario);

        if(usuario.isEmpty()){
            log.error(USUARIO_NO_ENCONTRADO);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, USUARIO_NO_ENCONTRADO);
        }

        return usuarioEntityToDtoConverter.convert(usuario.get());
    }

    private void validarUsuarioConMailNoExiste(String mail){
        if(usuarioRepository.findByMail(mail).isPresent()){
            log.error(String.format(USUARIO_YA_EXISTE, mail));
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(USUARIO_YA_EXISTE, mail));
        }
    }
}
