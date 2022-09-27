package org.gymanager.test.controller.implementation;

import org.gymanager.controller.implementation.MicroPlanControllerImpl;
import org.gymanager.model.client.MicroPlanDto;
import org.gymanager.model.client.MicroPlanDtoDetails;
import org.gymanager.model.enums.MicroPlanSortBy;
import org.gymanager.model.page.GyManagerPage;
import org.gymanager.service.specification.MicroPlanService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;
import static org.gymanager.test.constants.Constantes.ID_MICRO_PLAN;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MicroPlanControllerImplTest {

    @InjectMocks
    private MicroPlanControllerImpl microPlanController;

    @Mock
    private MicroPlanService microPlanService;

    @Test
    void getMicroPlanes_WhenOk_ThenReturnMicroPlanes() {
        var search = "filter";
        var esTemplate = Boolean.TRUE;
        var cantidadRutinas = 0;
        var excluirEliminados = Boolean.TRUE;
        var page = 0;
        var pageSize = 20;
        var sortBy = MicroPlanSortBy.NONE;
        var direction = Sort.Direction.ASC;

        var microPlanDto = mock(MicroPlanDto.class);

        when(microPlanService.getMicroPlanes(search,
                esTemplate,
                cantidadRutinas,
                excluirEliminados,
                page,
                pageSize,
                sortBy,
                direction))
                .thenReturn(new GyManagerPage<>(microPlanDto));

        var result = microPlanController.getMicroPlanes(search,
                esTemplate,
                cantidadRutinas,
                excluirEliminados,
                page,
                pageSize,
                sortBy,
                direction);

        assertThat(result).isNotNull();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getContent()).containsExactly(microPlanDto);
    }

    @Test
    public void getMicroPlanById_WhenOk_ThenReturnMicroPlan(){
        var microPlanDtoDetails = mock(MicroPlanDtoDetails.class);

        when(microPlanService.getMicroPlanById(ID_MICRO_PLAN)).thenReturn(microPlanDtoDetails);

        var result = microPlanController.getMicroPlanById(ID_MICRO_PLAN);

        assertThat(result).isNotNull();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody()).isEqualTo(microPlanDtoDetails);
    }

    @Test
    public void addMicroPlan_WhenOk_ThenReturnIdMicroPlanNuevo(){
        var microPlanDtoRequest = mock(MicroPlanDtoDetails.class);

        when(microPlanService.addMicroPlan(microPlanDtoRequest)).thenReturn(ID_MICRO_PLAN);

        var result = microPlanController.addMicroPlan(microPlanDtoRequest);

        assertThat(result).isNotNull();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody()).isEqualTo(ID_MICRO_PLAN);
    }

    @Test
    public void updateMicroPlanById_WhenOk_ThenReturnNull(){
        var microPlanDtoRequest = mock(MicroPlanDtoDetails.class);

        var result = microPlanController.updateMicroPlanById(ID_MICRO_PLAN, microPlanDtoRequest);

        assertThat(result).isNotNull();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(result.getBody()).isNull();

        verify(microPlanService).updateMicroPlanById(ID_MICRO_PLAN, microPlanDtoRequest);
    }

    @Test
    public void deleteMicroPlanById_WhenOk_ThenReturnNull(){
        var result = microPlanController.deleteMicroPlanById(ID_MICRO_PLAN);

        assertThat(result).isNotNull();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(result.getBody()).isNull();

        verify(microPlanService).deleteMicroPlanById(ID_MICRO_PLAN);
    }
}