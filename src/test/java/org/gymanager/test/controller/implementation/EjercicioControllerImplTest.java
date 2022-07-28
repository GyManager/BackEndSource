package org.gymanager.test.controller.implementation;

import org.gymanager.controller.implementation.EjercicioControllerImpl;
import org.gymanager.model.client.EjercicioDto;
import org.gymanager.model.client.EjercicioDtoRequest;
import org.gymanager.model.enums.EjercicioSortBy;
import org.gymanager.model.page.GyManagerPage;
import org.gymanager.service.specification.EjercicioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;
import static org.gymanager.test.constants.Constantes.ID_EJERCICIO;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EjercicioControllerImplTest {

    @InjectMocks
    private EjercicioControllerImpl ejercicioController;

    @Mock
    private EjercicioService ejercicioService;

    @Test
    public void getEjercicios_WhenOk_ThenReturnEjercicios(){
        var search = "filter";
        var page = 0;
        var pageSize = 20;
        var sortBy = EjercicioSortBy.NONE;
        var direction = Sort.Direction.ASC;

        var ejercicioDto = mock(EjercicioDto.class);

        when(ejercicioService.getEjercicios(search, page, pageSize, sortBy, direction))
                .thenReturn(new GyManagerPage<>(ejercicioDto));

        var result = ejercicioController.getEjercicios(search, page, pageSize, sortBy, direction);

        assertThat(result).isNotNull();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getContent()).containsExactly(ejercicioDto);
    }

    @Test
    public void getEjercicioById_WhenOk_ThenReturnEjercicio(){
        var ejercicioDto = mock(EjercicioDto.class);

        when(ejercicioService.getEjercicioById(ID_EJERCICIO))
                .thenReturn(ejercicioDto);

        var result = ejercicioController.getEjercicioById(ID_EJERCICIO);

        assertThat(result).isNotNull();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody()).isEqualTo(ejercicioDto);
    }

    @Test
    public void addEjercicio_WhenOk_ThenReturnIdEjercicioNuevo(){
        var ejercicioDtoRequest = mock(EjercicioDtoRequest.class);

        when(ejercicioService.addEjercicio(ejercicioDtoRequest)).thenReturn(ID_EJERCICIO);

        var result = ejercicioController.addEjercicio(ejercicioDtoRequest);

        assertThat(result).isNotNull();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody()).isEqualTo(ID_EJERCICIO);
    }

    @Test
    public void updateEjercicioById_WhenOk_ThenReturnNull(){
        var ejercicioDtoRequest = mock(EjercicioDtoRequest.class);

        var result = ejercicioController.updateEjercicioById(ID_EJERCICIO, ejercicioDtoRequest);

        assertThat(result).isNotNull();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(result.getBody()).isNull();

        verify(ejercicioService).updateEjercicioById(ID_EJERCICIO, ejercicioDtoRequest);
    }
}