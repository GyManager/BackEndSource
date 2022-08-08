package org.gymanager.test.controller.implementation;

import org.gymanager.controller.implementation.EjercicioAplicadoControllerImpl;
import org.gymanager.model.client.EjercicioAplicadoDto;
import org.gymanager.service.specification.EjercicioAplicadoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.gymanager.test.constants.Constantes.ID_MICRO_PLAN;
import static org.gymanager.test.constants.Constantes.ID_RUTINA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EjercicioAplicadoControllerImplTest {

    @InjectMocks
    private EjercicioAplicadoControllerImpl ejercicioAplicadoController;

    @Mock
    private EjercicioAplicadoService ejercicioAplicadoService;

    @Test
    public void getEjerciciosAplicadosByIdRutina_WhenOk_ThenReturnEjAp(){
        var ejercicioAplicadoDto = mock(EjercicioAplicadoDto.class);

        when(ejercicioAplicadoService.getEjerciciosAplicadosByIdRutina(ID_MICRO_PLAN, ID_RUTINA))
                .thenReturn(List.of(ejercicioAplicadoDto));

        var result = ejercicioAplicadoController.getEjerciciosAplicadosByIdRutina(ID_MICRO_PLAN, ID_RUTINA);

        assertThat(result).isNotNull();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody()).containsExactly(ejercicioAplicadoDto);
    }
}