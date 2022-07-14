package org.gymanager.test.controller.implementation;

import org.gymanager.controller.implementation.UsuarioControllerImpl;
import org.gymanager.model.client.UsuarioDto;
import org.gymanager.model.client.UsuarioDtoRegistro;
import org.gymanager.orchestrator.specification.UsuarioOrchestrator;
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
    private UsuarioOrchestrator usuarioOrchestrator;

    @Test
    public void addUsuario_WhenOk_ThenCreated(){
        UsuarioDtoRegistro usuarioDtoRegistro = new UsuarioDtoRegistro();

        when(usuarioOrchestrator.addUsuario(usuarioDtoRegistro)).thenReturn(ID_USUARIO);

        ResponseEntity<Long> resultado = usuarioController.addUsuario(usuarioDtoRegistro);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(resultado.getBody()).isEqualTo(ID_USUARIO);

        verify(usuarioOrchestrator).addUsuario(usuarioDtoRegistro);
    }

    @Test
    public void getUsuarios_WhenOk_ThenReturnUsuarios(){
        List<UsuarioDto> usuarioDtoList = List.of(new UsuarioDto());

        when(usuarioOrchestrator.getUsuarios()).thenReturn(usuarioDtoList);

        ResponseEntity<List<UsuarioDto>> resultado = usuarioController.getUsuarios();

        assertThat(resultado).isNotNull();
        assertThat(resultado.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(resultado.getBody()).isEqualTo(usuarioDtoList);

        verify(usuarioOrchestrator).getUsuarios();
    }

    @Test
    public void getUsuarioById_WhenOk_ThenReturnUsuario(){
        UsuarioDto usuarioDto = new UsuarioDto();

        when(usuarioOrchestrator.getUsuarioById(ID_USUARIO)).thenReturn(usuarioDto);

        ResponseEntity<UsuarioDto> resultado = usuarioController.getUsuarioById(ID_USUARIO);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(resultado.getBody()).isEqualTo(usuarioDto);

        verify(usuarioOrchestrator).getUsuarioById(ID_USUARIO);
    }

    @Test
    public void updateUsuarioById_WhenOk_ThenReturnNoContent(){
        UsuarioDtoRegistro usuarioDtoRegistro = new UsuarioDtoRegistro();

        ResponseEntity<Void> resultado = usuarioController.updateUsuarioById(ID_USUARIO, usuarioDtoRegistro);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        verify(usuarioOrchestrator).updateUsuarioById(ID_USUARIO, usuarioDtoRegistro);
    }

    @Test
    public void deleteUsuarioById_WhenOk_ThenReturnNoContent(){
        ResponseEntity<Void> resultado = usuarioController.deleteUsuarioById(ID_USUARIO);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        verify(usuarioOrchestrator).deleteUsuarioById(ID_USUARIO);
    }

}