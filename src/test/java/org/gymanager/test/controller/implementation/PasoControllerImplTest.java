package org.gymanager.test.controller.implementation;

import org.gymanager.controller.implementation.PasoControllerImpl;
import org.gymanager.model.client.PasoDto;
import org.gymanager.service.specification.PasoService;
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
class PasoControllerImplTest {

    @InjectMocks
    private PasoControllerImpl pasoController;

    @Mock
    private PasoService pasoService;

    @Test
    public void getPasosByIdEjercicio_WhenOk_ThenReturnPasos(){
        var pasoDto = mock(PasoDto.class);

        when(pasoService.getPasosByIdEjercicio(ID_EJERCICIO))
                .thenReturn(List.of(pasoDto));

        var result = pasoController.getPasosByIdEjercicio(ID_EJERCICIO);

        assertThat(result).isNotNull();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody()).containsExactly(pasoDto);
    }
}