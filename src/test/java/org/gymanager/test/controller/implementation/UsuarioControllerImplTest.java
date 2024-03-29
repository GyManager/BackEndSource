package org.gymanager.test.controller.implementation;

import org.gymanager.controller.implementation.UsuarioControllerImpl;
import org.gymanager.model.client.ClienteDto;
import org.gymanager.model.client.UsuarioDto;
import org.gymanager.model.client.UsuarioDtoDetails;
import org.gymanager.model.enums.ClienteSortBy;
import org.gymanager.model.enums.UsuarioSortBy;
import org.gymanager.model.page.GyManagerPage;
import org.gymanager.service.specification.UsuarioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.gymanager.test.constants.Constantes.ID_USUARIO;
import static org.mockito.Mockito.mock;
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
        UsuarioDtoDetails usuarioDto = new UsuarioDtoDetails();

        when(usuarioService.addUsuario(usuarioDto)).thenReturn(ID_USUARIO);

        ResponseEntity<Long> resultado = usuarioController.addUsuario(usuarioDto);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(resultado.getBody()).isEqualTo(ID_USUARIO);

        verify(usuarioService).addUsuario(usuarioDto);
    }

    @Test
    public void getUsuarios_WhenOk_ThenReturnUsuarios(){
        var search = "";
        var page = 1;
        var size = 10;
        var sortBy = UsuarioSortBy.NONE;
        var direction = Sort.Direction.ASC;
        var usuarioDtoGyManagerPage = new GyManagerPage<>(new UsuarioDto());

        when(usuarioService.getUsuarios(search, page, size, sortBy, direction)).thenReturn(usuarioDtoGyManagerPage);

        ResponseEntity<GyManagerPage<UsuarioDto>> resultado = usuarioController.getUsuarios(search, page, size, sortBy, direction);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(resultado.getBody()).isEqualTo(usuarioDtoGyManagerPage);

        verify(usuarioService).getUsuarios(search, page, size, sortBy, direction);
    }

    @Test
    public void getUsuarioById_WhenOk_ThenReturnUsuario(){
        UsuarioDtoDetails usuarioDto = new UsuarioDtoDetails();

        when(usuarioService.getUsuarioById(ID_USUARIO)).thenReturn(usuarioDto);

        ResponseEntity<UsuarioDtoDetails> resultado = usuarioController.getUsuarioById(ID_USUARIO);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(resultado.getBody()).isEqualTo(usuarioDto);

        verify(usuarioService).getUsuarioById(ID_USUARIO);
    }

    @Test
    public void updateUsuarioById_WhenOk_ThenReturnNoContent(){
        var usuarioDtoDetails = new UsuarioDtoDetails();

        ResponseEntity<Void> resultado = usuarioController.updateUsuarioById(ID_USUARIO, usuarioDtoDetails);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        verify(usuarioService).updateUsuarioById(ID_USUARIO, usuarioDtoDetails);
    }

    @Test
    public void deleteUsuarioById_WhenOk_ThenReturnNoContent(){
        ResponseEntity<Void> resultado = usuarioController.deleteUsuarioById(ID_USUARIO);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        verify(usuarioService).deleteUsuarioById(ID_USUARIO);
    }

}