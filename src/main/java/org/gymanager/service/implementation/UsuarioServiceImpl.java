package org.gymanager.service.implementation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.gymanager.converter.UsuarioEntityToDtoConverter;
import org.gymanager.converter.UsuarioEntityToDtoDetailsConverter;
import org.gymanager.model.client.UsuarioDto;
import org.gymanager.model.client.UsuarioDtoDetails;
import org.gymanager.model.domain.Permiso;
import org.gymanager.model.domain.Rol;
import org.gymanager.model.domain.Sexo;
import org.gymanager.model.domain.TipoDocumento;
import org.gymanager.model.domain.Usuario;
import org.gymanager.repository.specification.UsuarioRepository;
import org.gymanager.service.specification.RolService;
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
    private final UsuarioEntityToDtoDetailsConverter usuarioEntityToDtoDetailsConverter;

    @NonNull
    private final TipoDocumentoService tipoDocumentoService;

    @NonNull
    private final SexoService sexoService;

    @NonNull
    private final RolService rolService;

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
    public UsuarioDtoDetails getUsuarioById(Long idUsuario) {
        return usuarioEntityToDtoDetailsConverter.convert(getUsuarioEntityById(idUsuario));
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
    public Long addUsuario(UsuarioDtoDetails usuarioDtoDetails) {
        return addUsuario(usuarioDtoDetails, usuarioDtoDetails.getRoles());
    }

    @Override
    public Long addUsuario(UsuarioDto usuarioDto, List<String> roles) {
        validarUsuarioConMailNoExiste(usuarioDto.getMail());

        var tipoDocumento = tipoDocumentoService.getTipoDocumentoByTipo(usuarioDto.getTipoDocumento());
        validarUsuarioConNroYTipoDocumentoNoExiste(tipoDocumento, usuarioDto.getNumeroDocumento());

        var sexo = buscarSexoByName(usuarioDto.getSexo());
        var rolEntities = rolService.getRolEntitiesByRolNames(roles);

        Usuario usuario = new Usuario();
        usuario.setNumeroDocumento(usuarioDto.getNumeroDocumento());
        usuario.setTipoDocumento(tipoDocumento);
        usuario.setNombre(usuarioDto.getNombre());
        usuario.setApellido(usuarioDto.getApellido());
        usuario.setSexo(sexo);
        usuario.setMail(usuarioDto.getMail());
        usuario.setCelular(usuarioDto.getCelular());
        usuario.setFechaAlta(LocalDate.now());
        usuario.setRoles(rolEntities);

        usuario.setPass(passwordEncoder.encode(usuarioDto.getNumeroDocumento().toString()));

        return usuarioRepository.save(usuario).getIdUsuario();
    }

    @Override
    public void updateUsuarioById(Long idUsuario, UsuarioDtoDetails usuarioDtoDetails) {
        updateUsuarioById(idUsuario, usuarioDtoDetails, usuarioDtoDetails.getRoles(), Boolean.TRUE);
    }

    @Override
    public void updateUsuarioById(Long idUsuario, UsuarioDto usuarioDto, List<String> roles, Boolean updateRoles) {
        var usuario = getUsuarioEntityById(idUsuario);

        if(!usuarioDto.getMail().equals(usuario.getMail())){
            validarUsuarioConMailNoExiste(usuarioDto.getMail());
        }

        var tipoDocumento = tipoDocumentoService.getTipoDocumentoByTipo(usuarioDto.getTipoDocumento());
        if(!usuarioDto.getNumeroDocumento().equals(usuario.getNumeroDocumento())
                || !usuarioDto.getTipoDocumento().equals(usuario.getTipoDocumento().getTipo())){
            validarUsuarioConNroYTipoDocumentoNoExiste(tipoDocumento, usuarioDto.getNumeroDocumento());
        }

        var sexo = buscarSexoByName(usuarioDto.getSexo());

        usuario.setNumeroDocumento(usuarioDto.getNumeroDocumento());
        usuario.setTipoDocumento(tipoDocumento);
        usuario.setNombre(usuarioDto.getNombre());
        usuario.setApellido(usuarioDto.getApellido());
        usuario.setSexo(sexo);
        usuario.setMail(usuarioDto.getMail());
        usuario.setCelular(usuarioDto.getCelular());
        if(updateRoles) {
            var rolEntities = rolService.getRolEntitiesByRolNames(roles);
            usuario.setRoles(rolEntities);
        }

        usuarioRepository.save(usuario);
    }

    @Override
    public void deleteUsuarioById(Long idUsuario) {
        Usuario usuario = getUsuarioEntityById(idUsuario);

        usuarioRepository.delete(usuario);
    }

    @Override
    public Usuario getUsuarioEntityByMail(String mail) {
        Optional<Usuario> usuario = usuarioRepository.findByMail(mail);

        if (usuario.isEmpty()) {
            log.error(String.format(USUARIO_CON_MAIL_NO_ENCONTRADO, mail));
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format(USUARIO_CON_MAIL_NO_ENCONTRADO, mail));
        }

        return usuario.get();
    }

    private void validarUsuarioConMailNoExiste(String mail){
        if(usuarioRepository.findByMail(mail).isPresent()){
            log.error(String.format(MAIL_EN_USO, mail));
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(MAIL_EN_USO, mail));
        }
    }

    private void validarUsuarioConNroYTipoDocumentoNoExiste(TipoDocumento tipoDocumento, Long numeroDocumento) {
        if(usuarioRepository.findByTipoDocumentoAndNumeroDocumento(tipoDocumento, numeroDocumento).isPresent()){
            log.error(String.format(NUMERO_TIPO_DOCUMENTO_EN_USO, numeroDocumento, tipoDocumento.getTipo()));
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format(NUMERO_TIPO_DOCUMENTO_EN_USO, numeroDocumento, tipoDocumento.getTipo()));
        }
    }

    private Sexo buscarSexoByName(String sexo){
        return StringUtils.isEmpty(sexo) ? null :
                sexoService.getSexoByNombreSexo(sexo);
    }
}
