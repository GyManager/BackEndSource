package org.gymanager.test.controller.implementation;

import org.gymanager.controller.implementation.UsuarioControllerImpl;
import org.gymanager.model.client.usuarios.UsuarioDto;
import org.gymanager.model.client.usuarios.UsuarioDtoRegistro;
import org.gymanager.service.specification.UsuarioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsuarioControllerImplTest {

    @InjectMocks
    private UsuarioControllerImpl usuarioController;

    @Mock
    private UsuarioService usuarioService;

    @Test
    public void addUsuario_WhenOk_ThenCreated(){
        UsuarioDtoRegistro usuarioDtoRegistro = new UsuarioDtoRegistro();
        UsuarioDto usuarioDto = new UsuarioDto();

        when(usuarioService.addUsuario(usuarioDtoRegistro)).thenReturn(usuarioDto);

        ResponseEntity<UsuarioDto> resultado = usuarioController.addUsuario(usuarioDtoRegistro);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(resultado.getBody()).isEqualTo(usuarioDto);

        verify(usuarioService).addUsuario(usuarioDtoRegistro);
    }

    @Test
    public void getUsuarios_WhenOk_ThenReturnUsuarios(){
        List<UsuarioDto> usuarioDtoList = List.of(new UsuarioDto());

        when(usuarioService.getUsuarios()).thenReturn(usuarioDtoList);

        ResponseEntity<List<UsuarioDto>> resultado = usuarioController.getUsuarios();

        assertThat(resultado).isNotNull();
        assertThat(resultado.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(resultado.getBody()).isEqualTo(usuarioDtoList);

        verify(usuarioService).getUsuarios();
    }

}