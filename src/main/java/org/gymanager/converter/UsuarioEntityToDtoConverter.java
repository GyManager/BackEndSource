package org.gymanager.converter;

import lombok.RequiredArgsConstructor;
import org.gymanager.model.client.UsuarioDto;
import org.gymanager.model.domain.Usuario;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class UsuarioEntityToDtoConverter implements GyManagerConverter<Usuario, UsuarioDto> {

    @Override
    public UsuarioDto convert(Usuario source) {
        UsuarioDto usuarioDto = new UsuarioDto();
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
        return usuarioDto;
    }
}
