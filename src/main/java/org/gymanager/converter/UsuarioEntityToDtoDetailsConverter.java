package org.gymanager.converter;

import lombok.RequiredArgsConstructor;
import org.gymanager.model.client.UsuarioDto;
import org.gymanager.model.client.UsuarioDtoDetails;
import org.gymanager.model.domain.Rol;
import org.gymanager.model.domain.Usuario;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class UsuarioEntityToDtoDetailsConverter implements GyManagerConverter<Usuario, UsuarioDtoDetails> {

    @Override
    public UsuarioDtoDetails convert(Usuario source) {
        var usuarioDto = new UsuarioDtoDetails();
        usuarioDto.setIdUsuario(source.getIdUsuario());
        usuarioDto.setNumeroDocumento(source.getNumeroDocumento());
        usuarioDto.setTipoDocumento(Objects.isNull(source.getTipoDocumento()) ? null : source.getTipoDocumento().getTipo());
        usuarioDto.setNombre(source.getNombre());
        usuarioDto.setApellido(source.getApellido());
        usuarioDto.setSexo(Objects.isNull(source.getSexo()) ? null : source.getSexo().getSexo());
        usuarioDto.setFechaAlta(source.getFechaAlta());
        usuarioDto.setFechaBaja(source.getFechaBaja());
        usuarioDto.setMail(source.getMail());
        usuarioDto.setCelular(source.getCelular());
        usuarioDto.setRoles(Objects.isNull(source.getRoles()) ? null : source.getRoles().stream().map(Rol::getNombreRol).toList());
        return usuarioDto;
    }
}
