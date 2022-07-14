package org.gymanager.service.implementation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.gymanager.converter.UsuarioEntityToDtoConverter;
import org.gymanager.model.client.UsuarioDto;
import org.gymanager.model.client.UsuarioDtoRegistro;
import org.gymanager.model.domain.Permiso;
import org.gymanager.model.domain.Rol;
import org.gymanager.model.domain.TipoDocumento;
import org.gymanager.model.domain.Usuario;
import org.gymanager.repository.specification.UsuarioRepository;
import org.gymanager.service.specification.SexoService;
import org.gymanager.service.specification.TipoDocumentoService;
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

import javax.transaction.Transactional;
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
    private static final String MAIL_EN_USO = "Ya existe un usuario registrado con el mail (%s)";
    private static final String NUMERO_TIPO_DOCUMENTO_EN_USO = "Ya existe un usuario registrado con el numero (%s)" +
            " y tipo (%s) de documento";
    private static final String MAIL_VACIO = "El mail de login no debe ser vacio";
    private static final String PASS_NO_COINCIDEN = "La contraseña y la confirmacion de la contraseña no coinciden";

    @NonNull
    private UsuarioRepository usuarioRepository;

    @NonNull
    private final UsuarioEntityToDtoConverter usuarioEntityToDtoConverter;

    @NonNull
    private final TipoDocumentoService tipoDocumentoService;

    @NonNull
    private final SexoService sexoService;

    @NonNull
    private final PasswordEncoder passwordEncoder;

    /**
     * Implementacion del metodo de UserDetailsService para validar las credenciales de login
     */
    @Override
    @Transactional
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
                .distinct()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return new User(usuario.get().getMail(),
                usuario.get().getPass(),
                authorities);
    }

    @Override
    public List<UsuarioDto> getUsuarios() {
        return usuarioEntityToDtoConverter.convert(usuarioRepository.findAll());
    }

    @Override
    public UsuarioDto getUsuarioById(Long idUsuario) {
        return usuarioEntityToDtoConverter.convert(getUsuarioEntityById(idUsuario));
    }

    @Override
    public Usuario getUsuarioEntityById(Long idUsuario) {
        Optional<Usuario> usuario = usuarioRepository.findById(idUsuario);

        if(usuario.isEmpty()){
            log.error(USUARIO_NO_ENCONTRADO);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, USUARIO_NO_ENCONTRADO);
        }

        return usuario.get();
    }

    @Override
    public Long addUsuario(UsuarioDtoRegistro usuarioDtoRegistro) {
        validarConfirmacionPass(usuarioDtoRegistro);
        validarUsuarioConMailNoExiste(usuarioDtoRegistro.getMail());

        TipoDocumento tipoDocumento = tipoDocumentoService.getTipoDocumentoByTipo(usuarioDtoRegistro.getTipoDocumento());
        validarUsuarioConNroYTipoDocumentoNoExiste(tipoDocumento, usuarioDtoRegistro.getNumeroDocumento());

        Usuario usuario = new Usuario();
        usuario.setNumeroDocumento(usuarioDtoRegistro.getNumeroDocumento());
        usuario.setTipoDocumento(tipoDocumento);
        usuario.setNombre(usuarioDtoRegistro.getNombre());
        usuario.setApellido(usuarioDtoRegistro.getApellido());

        if(!StringUtils.isEmpty(usuarioDtoRegistro.getSexo())){
            usuario.setSexo(sexoService.getSexoByNombreSexo(usuarioDtoRegistro.getSexo()));
        }
        usuario.setMail(usuarioDtoRegistro.getMail());
        usuario.setCelular(usuarioDtoRegistro.getCelular());
        usuario.setPass(passwordEncoder.encode(usuarioDtoRegistro.getPass()));
        usuario.setFechaAlta(LocalDate.now());

        return usuarioRepository.save(usuario).getIdUsuario();
    }

    @Override
    public void updateUsuarioById(Long idUsuario, UsuarioDtoRegistro usuarioDtoRegistro) {

        Usuario usuario = getUsuarioEntityById(idUsuario);

        TipoDocumento tipoDocumento = tipoDocumentoService.getTipoDocumentoByTipo(usuarioDtoRegistro.getTipoDocumento());

        usuario.setNumeroDocumento(usuarioDtoRegistro.getNumeroDocumento());
        usuario.setTipoDocumento(tipoDocumento);
        usuario.setNombre(usuarioDtoRegistro.getNombre());
        usuario.setApellido(usuarioDtoRegistro.getApellido());

        if(!StringUtils.isEmpty(usuarioDtoRegistro.getSexo())){
            usuario.setSexo(sexoService.getSexoByNombreSexo(usuarioDtoRegistro.getSexo()));
        }
        usuario.setMail(usuarioDtoRegistro.getMail());
        usuario.setCelular(usuarioDtoRegistro.getCelular());

        if(StringUtils.isEmpty(usuarioDtoRegistro.getPass())){
            validarConfirmacionPass(usuarioDtoRegistro);
            usuario.setPass(passwordEncoder.encode(usuarioDtoRegistro.getPass()));
        }
        usuario.setFechaAlta(LocalDate.now());

        usuarioRepository.save(usuario);
    }

    @Override
    public void deleteUsuarioById(Long idUsuario) {
        Usuario usuario = getUsuarioEntityById(idUsuario);

        usuarioRepository.delete(usuario);
    }

    private void validarUsuarioConMailNoExiste(String mail){
        if(usuarioRepository.findByMail(mail).isPresent()){
            log.error(String.format(MAIL_EN_USO, mail));
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(MAIL_EN_USO, mail));
        }
    }

    private void validarConfirmacionPass(UsuarioDtoRegistro usuarioDtoRegistro){
        if(!usuarioDtoRegistro.getPass().equals(usuarioDtoRegistro.getConfirmacionPass())){
            log.error(String.format(PASS_NO_COINCIDEN));
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(PASS_NO_COINCIDEN));
        }
    }

    private void validarUsuarioConNroYTipoDocumentoNoExiste(TipoDocumento tipoDocumento, Long numeroDocumento) {
        if(usuarioRepository.findByTipoDocumentoAndNumeroDocumento(tipoDocumento, numeroDocumento).isPresent()){
            log.error(String.format(NUMERO_TIPO_DOCUMENTO_EN_USO, numeroDocumento, tipoDocumento.getTipo()));
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format(NUMERO_TIPO_DOCUMENTO_EN_USO, numeroDocumento, tipoDocumento.getTipo()));
        }
    }
}
