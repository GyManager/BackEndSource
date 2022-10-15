package org.gymanager.test.service.implementation;

import org.gymanager.converter.MicroPlanEntityToDtoConverter;
import org.gymanager.converter.MicroPlanEntityToDtoDetailsConverter;
import org.gymanager.model.client.MicroPlanDto;
import org.gymanager.model.client.MicroPlanDtoDetails;
import org.gymanager.model.client.RutinaDtoDetails;
import org.gymanager.model.domain.MicroPlan;
import org.gymanager.model.domain.Rutina;
import org.gymanager.model.enums.MicroPlanSortBy;
import org.gymanager.repository.filters.MicroPlanSpecification;
import org.gymanager.repository.specification.MicroPlanRepository;
import org.gymanager.service.implementation.MicroPlanServiceImpl;
import org.gymanager.service.specification.ObservacionService;
import org.gymanager.service.specification.RutinaService;
import org.gymanager.service.specification.UsuarioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.gymanager.test.constants.Constantes.ID_MICRO_PLAN;
import static org.gymanager.test.constants.Constantes.NOMBRE_MICRO_PLAN;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MicroPlanServiceImplTest {

    @InjectMocks
    private MicroPlanServiceImpl microPlanService;

    @Mock
    private MicroPlanRepository microPlanRepository;

    @Mock
    private MicroPlanEntityToDtoConverter microPlanEntityToDtoConverter;

    @Mock
    private MicroPlanEntityToDtoDetailsConverter microPlanEntityToDtoDetailsConverter;

    @Mock
    private RutinaService rutinaService;

    @Mock
    private ObservacionService observacionService;

    @Mock
    private UsuarioService usuarioService;

    @Captor
    private ArgumentCaptor<MicroPlan> microPlanArgumentCaptor;

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

        var microPlan = mock(MicroPlan.class);
        var microPlanDto = mock(MicroPlanDto.class);

        when(microPlanRepository.findAll(any(MicroPlanSpecification.class), any(PageRequest.class)))
                .thenReturn(new PageImpl<>(List.of(microPlan)));
        when(microPlanEntityToDtoConverter.convert(microPlan)).thenReturn(microPlanDto);

        var result = microPlanService.getMicroPlanes(search,
                esTemplate,
                cantidadRutinas,
                excluirEliminados,
                page,
                pageSize,
                sortBy,
                direction);

        assertThat(result).isNotNull();
        assertThat(result.getContent()).containsExactly(microPlanDto);
    }

    @Test
    void getMicroPlanById_WhenOk_ThenReturnMicroPlanDto() {
        var microPlan = mock(MicroPlan.class);
        var microPlanDtoDetails = mock(MicroPlanDtoDetails.class);

        when(microPlanRepository.findById(ID_MICRO_PLAN)).thenReturn(Optional.of(microPlan));
        when(microPlanEntityToDtoDetailsConverter.convert(microPlan)).thenReturn(microPlanDtoDetails);

        var result = microPlanService.getMicroPlanById(ID_MICRO_PLAN, Boolean.FALSE);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(microPlanDtoDetails);
    }

    @Test
    void getMicroPlanEntityById_WhenOk_ThenReturnMicroPlanEntity() {
        var microPlan = mock(MicroPlan.class);

        when(microPlanRepository.findById(ID_MICRO_PLAN)).thenReturn(Optional.of(microPlan));

        var result = microPlanService.getMicroPlanEntityById(ID_MICRO_PLAN);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(microPlan);
    }


    @Test
    public void getMicroPlanEntityById_WhenIdNotFound_ThenThrowNotFound(){
        when(microPlanRepository.findById(ID_MICRO_PLAN)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> microPlanService.getMicroPlanEntityById(ID_MICRO_PLAN))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Micro plan no encontrado")
                .extracting("status").isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void addMicroPlan() {
        var rutinaDto = mock(RutinaDtoDetails.class);

        var microPlanDtoRequest = new MicroPlanDtoDetails();
        microPlanDtoRequest.setNombre(NOMBRE_MICRO_PLAN);
        microPlanDtoRequest.setEsTemplate(Boolean.TRUE);
        microPlanDtoRequest.setRutinas(List.of(rutinaDto));

        var rutina = new Rutina();

        var microPlan = new MicroPlan();
        microPlan.setIdMicroPlan(ID_MICRO_PLAN);

        when(rutinaService.crearRutinas(microPlanDtoRequest.getRutinas())).thenReturn(List.of(rutina));
        when(microPlanRepository.save(any(MicroPlan.class))).thenReturn(microPlan);

        var result = microPlanService.addMicroPlan(microPlanDtoRequest);

        assertThat(result).isEqualTo(ID_MICRO_PLAN);

        verify(microPlanRepository).save(microPlanArgumentCaptor.capture());
        var microPlanCreado = microPlanArgumentCaptor.getValue();
        assertThat(microPlanCreado.getNombre()).isEqualTo(NOMBRE_MICRO_PLAN);
        assertThat(microPlanCreado.getEsTemplate()).isTrue();
        assertThat(microPlanCreado.getNumeroOrden()).isNull();
        assertThat(microPlanCreado.getRutinas()).containsExactly(rutina);
    }

    @Test
    void updateMicroPlanById() {
        var rutinaDto = mock(RutinaDtoDetails.class);

        var microPlanDtoRequest = new MicroPlanDtoDetails();
        microPlanDtoRequest.setIdMicroPlan(ID_MICRO_PLAN);
        microPlanDtoRequest.setNombre(NOMBRE_MICRO_PLAN);
        microPlanDtoRequest.setEsTemplate(Boolean.TRUE);
        microPlanDtoRequest.setRutinas(List.of(rutinaDto));

        var microPlan = new MicroPlan();
        microPlan.setIdMicroPlan(ID_MICRO_PLAN);
        microPlan.setNombre(NOMBRE_MICRO_PLAN.concat("Cambiado"));
        microPlan.setEsTemplate(Boolean.FALSE);

        when(microPlanRepository.findById(ID_MICRO_PLAN)).thenReturn(Optional.of(microPlan));

        microPlanService.updateMicroPlanById(ID_MICRO_PLAN, microPlanDtoRequest);

        verify(rutinaService).actualizarRutinasMicroPlan(microPlanDtoRequest.getRutinas(), microPlan);

        verify(microPlanRepository).save(microPlanArgumentCaptor.capture());
        var microPlanCreado = microPlanArgumentCaptor.getValue();
        assertThat(microPlanCreado.getIdMicroPlan()).isEqualTo(ID_MICRO_PLAN);
        assertThat(microPlanCreado.getNombre()).isEqualTo(NOMBRE_MICRO_PLAN);
        assertThat(microPlanCreado.getEsTemplate()).isTrue();
        assertThat(microPlanCreado.getNumeroOrden()).isNull();
    }

    @Test
    void deleteMicroPlanById() {
        var microPlan = new MicroPlan();

        when(microPlanRepository.findById(ID_MICRO_PLAN)).thenReturn(Optional.of(microPlan));

        microPlanService.deleteMicroPlanById(ID_MICRO_PLAN);

        verify(microPlanRepository).delete(microPlan);
    }
}