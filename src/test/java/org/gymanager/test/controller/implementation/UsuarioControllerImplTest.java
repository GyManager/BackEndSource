package org.gymanager.test.controller.implementation;

import org.gymanager.controller.implementation.UsuarioControllerImpl;
import org.gymanager.model.client.UsuarioDto;
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
import static org.gymanager.test.constants.Constantes.ID_USUARIO;
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
        UsuarioDto usuarioDto = new UsuarioDto();

        when(usuarioService.addUsuario(usuarioDto)).thenReturn(ID_USUARIO);

        ResponseEntity<Long> resultado = usuarioController.addUsuario(usuarioDto);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(resultado.getBody()).isEqualTo(ID_USUARIO);

        verify(usuarioService).addUsuario(usuarioDto);
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

    @Test
    public void getUsuarioById_WhenOk_ThenReturnUsuario(){
        UsuarioDto usuarioDto = new UsuarioDto();

        when(usuarioService.getUsuarioById(ID_USUARIO)).thenReturn(usuarioDto);

        ResponseEntity<UsuarioDto> resultado = usuarioController.getUsuarioById(ID_USUARIO);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(resultado.getBody()).isEqualTo(usuarioDto);

        verify(usuarioService).getUsuarioById(ID_USUARIO);
    }

    @Test
    public void updateUsuarioById_WhenOk_ThenReturnNoContent(){
        UsuarioDto usuarioDto = new UsuarioDto();

        ResponseEntity<Void> resultado = usuarioController.updateUsuarioById(ID_USUARIO, usuarioDto);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        verify(usuarioService).updateUsuarioById(ID_USUARIO, usuarioDto);
    }

    @Test
    public void deleteUsuarioById_WhenOk_ThenReturnNoContent(){
        ResponseEntity<Void> resultado = usuarioController.deleteUsuarioById(ID_USUARIO);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        verify(usuarioService).deleteUsuarioById(ID_USUARIO);
    }

}