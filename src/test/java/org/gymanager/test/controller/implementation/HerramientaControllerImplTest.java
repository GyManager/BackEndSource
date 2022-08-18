package org.gymanager.test.controller.implementation;

import org.gymanager.controller.implementation.HerramientasControllerImpl;
import org.gymanager.model.client.HerramientaDto;
import org.gymanager.service.specification.HerramientaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.gymanager.test.constants.Constantes.ID_EJERCICIO;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HerramientaControllerImplTest {

    @InjectMocks
    private HerramientasControllerImpl herramientasController;

    @Mock
    private HerramientaService herramientaService;

    @Test
    public void getHerramientasByIdEjercicio_WhenOk_ThenReturnHerramientas(){
        var herramientaDto = mock(HerramientaDto.class);

        when(herramientaService.getHerramientasByIdEjercicio(ID_EJERCICIO))
                .thenReturn(List.of(herramientaDto));

        var result = herramientasController.getHerramientasByIdEjercicio(ID_EJERCICIO);

        assertThat(result).isNotNull();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody()).containsExactly(herramientaDto);
    }

    @Test
    public void getHerramientas_WhenOk_ThenReturnHerramientas(){
        var herramientaDto = mock(HerramientaDto.class);

        when(herramientaService.getHerramientas()).thenReturn(List.of(herramientaDto));

        var result = herramientasController.getHerramientas();

        assertThat(result).isNotNull();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody()).containsExactly(herramientaDto);
    }
}