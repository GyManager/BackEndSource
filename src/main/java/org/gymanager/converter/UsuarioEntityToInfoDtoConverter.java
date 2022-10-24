package org.gymanager.converter;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.gymanager.model.client.UsuarioDtoDetails;
import org.gymanager.model.client.UsuarioInfoDto;
import org.gymanager.model.domain.Rol;
import org.gymanager.model.domain.Usuario;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class UsuarioEntityToInfoDtoConverter implements GyManagerConverter<Usuario, UsuarioInfoDto> {

    @NonNull
    private ClienteEntityToDtoConverter clienteEntityToDtoConverter;

    @Override
    public UsuarioInfoDto convert(Usuario source) {
        var usuarioDto = new UsuarioInfoDto();
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
        usuarioDto.setCliente(Objects.isNull(source.getCliente()) ? null :
                clienteEntityToDtoConverter.convert(source.getCliente()));
        return usuarioDto;
    }
}
