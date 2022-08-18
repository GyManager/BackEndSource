package org.gymanager.test.controller.implementation;

import org.gymanager.controller.implementation.RutinaControllerImpl;
import org.gymanager.model.client.RutinaDto;
import org.gymanager.service.specification.RutinaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.gymanager.test.constants.Constantes.ID_MICRO_PLAN;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RutinaControllerImplTest {

    @InjectMocks
    private RutinaControllerImpl rutinaController;

    @Mock
    private RutinaService rutinaService;

    @Test
    public void getRutinasByIdMicroPlan_WhenOk_ThenReturnRutinas(){
        var rutinaDto = mock(RutinaDto.class);

        when(rutinaService.getRutinasByIdMicroPlan(ID_MICRO_PLAN)).thenReturn(List.of(rutinaDto));

        var result = rutinaController.getRutinasByIdMicroPlan(ID_MICRO_PLAN);

        assertThat(result).isNotNull();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody()).containsExactly(rutinaDto);
    }
}