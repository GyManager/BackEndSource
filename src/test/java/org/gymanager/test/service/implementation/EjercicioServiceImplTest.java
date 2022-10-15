package org.gymanager.test.service.implementation;

import org.gymanager.converter.EjercicioEntityToDtoConverter;
import org.gymanager.model.client.EjercicioDto;
import org.gymanager.model.client.EjercicioDtoRequest;
import org.gymanager.model.client.PasoDto;
import org.gymanager.model.domain.Ejercicio;
import org.gymanager.model.domain.Herramienta;
import org.gymanager.model.domain.Paso;
import org.gymanager.model.domain.TipoEjercicio;
import org.gymanager.model.enums.EjercicioSortBy;
import org.gymanager.repository.filters.EjercicioSpecification;
import org.gymanager.repository.specification.EjercicioRepository;
import org.gymanager.service.implementation.EjercicioServiceImpl;
import org.gymanager.service.specification.HerramientaService;
import org.gymanager.service.specification.PasoService;
import org.gymanager.service.specification.TipoEjercicioService;
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
import static org.gymanager.test.constants.Constantes.ID_EJERCICIO;
import static org.gymanager.test.constants.Constantes.ID_HERRAMIENTA;
import static org.gymanager.test.constants.Constantes.ID_PASO;
import static org.gymanager.test.constants.Constantes.ID_TIPO_EJERCICIO;
import static org.gymanager.test.constants.Constantes.NOMBRE_EJERCICIO;
import static org.gymanager.test.constants.Constantes.TIPO_EJERCICIO;
import static org.gymanager.test.constants.Constantes.VIDEO_EJERCICIO;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EjercicioServiceImplTest {

    @InjectMocks
    private EjercicioServiceImpl ejercicioService;

    @Mock
    private EjercicioRepository ejercicioRepository;

    @Mock
    private EjercicioEntityToDtoConverter ejercicioEntityToDtoConverter;

    @Mock
    private TipoEjercicioService tipoEjercicioService;

    @Mock
    private PasoService pasoService;

    @Mock
    private HerramientaService herramientaService;

    @Captor
    private ArgumentCaptor<Ejercicio> ejercicioArgumentCaptor;


    @Test
    public void getEjercicios_WhenOk_ThenReturnEjercicios(){
        var search = "filter";
        var excluirEliminados = Boolean.TRUE;
        var page = 0;
        var pageSize = 20;
        var sortBy = EjercicioSortBy.NONE;
        var direction = Sort.Direction.ASC;

        var ejercicio = mock(Ejercicio.class);
        var ejercicioDto = mock(EjercicioDto.class);

        when(ejercicioRepository.findAll(any(EjercicioSpecification.class), any(PageRequest.class)))
                .thenReturn(new PageImpl<>(List.of(ejercicio)));
        when(ejercicioEntityToDtoConverter.convert(ejercicio)).thenReturn(ejercicioDto);

        var result = ejercicioService.getEjercicios(search, excluirEliminados, page, pageSize, sortBy, direction);

        assertThat(result).isNotNull();
        assertThat(result.getContent()).containsExactly(ejercicioDto);
    }

    @Test
    public void getEjercicioEntityById_WhenOk_ThenReturnEjercicio(){
        var ejercicio = mock(Ejercicio.class);

        when(ejercicioRepository.findById(ID_EJERCICIO)).thenReturn(Optional.of(ejercicio));

        var result = ejercicioService.getEjercicioEntityById(ID_EJERCICIO);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(ejercicio);
    }

    @Test
    public void getEjercicioById_WhenOk_ThenReturnEjercicio(){
        var ejercicio = mock(Ejercicio.class);
        var ejercicioDto = mock(EjercicioDto.class);

        when(ejercicioRepository.findById(ID_EJERCICIO)).thenReturn(Optional.of(ejercicio));
        when(ejercicioEntityToDtoConverter.convert(ejercicio)).thenReturn(ejercicioDto);

        var result = ejercicioService.getEjercicioById(ID_EJERCICIO);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(ejercicioDto);
    }

    @Test
    public void getEjercicioEntityById_WhenIdNotFound_ThenThrowNotFound(){
        when(ejercicioRepository.findById(ID_EJERCICIO)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> ejercicioService.getEjercicioEntityById(ID_EJERCICIO))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Ejercicio no encontrado")
                .extracting("status").isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void addEjercicio_WhenOk_ReturnIdEjercicioNuevo(){
        var pasoDto = new PasoDto();

        var ejercicioDtoRequest = new EjercicioDtoRequest();
        ejercicioDtoRequest.setNombre(NOMBRE_EJERCICIO);
        ejercicioDtoRequest.setVideo(VIDEO_EJERCICIO);
        ejercicioDtoRequest.setTipoEjercicio(TIPO_EJERCICIO);
        ejercicioDtoRequest.setIdHerramientaList(List.of(ID_HERRAMIENTA));
        ejercicioDtoRequest.setPasos(List.of(pasoDto));

        var tipoEjercicio = new TipoEjercicio();
        tipoEjercicio.setIdTipoEjercicio(ID_TIPO_EJERCICIO);

        var herramienta = new Herramienta();
        herramienta.setIdHerramienta(ID_HERRAMIENTA);

        var paso = new Paso();
        paso.setIdPaso(ID_PASO);

        var ejercicio = new Ejercicio();
        ejercicio.setIdEjercicio(ID_EJERCICIO);

        when(tipoEjercicioService.getTipoEjercicioByNombre(TIPO_EJERCICIO)).thenReturn(tipoEjercicio);
        when(ejercicioRepository.findByTipoEjercicioAndNombreIgnoreCaseAndFechaBajaIsNull(tipoEjercicio, NOMBRE_EJERCICIO))
                .thenReturn(Optional.empty());
        when(herramientaService.getHerramientasByIds(List.of(ID_HERRAMIENTA))).thenReturn(List.of(herramienta));
        when(pasoService.crearPasos(List.of(pasoDto))).thenReturn(List.of(paso));
        when(ejercicioRepository.save(any(Ejercicio.class))).thenReturn(ejercicio);

        var result = ejercicioService.addEjercicio(ejercicioDtoRequest);

        assertThat(result).isEqualTo(ID_EJERCICIO);

        verify(ejercicioRepository).save(ejercicioArgumentCaptor.capture());
        var ejercicioCreado = ejercicioArgumentCaptor.getValue();
        assertThat(ejercicioCreado.getTipoEjercicio()).isEqualTo(tipoEjercicio);
        assertThat(ejercicioCreado.getNombre()).isEqualTo(NOMBRE_EJERCICIO);
        assertThat(ejercicioCreado.getVideo()).isEqualTo(VIDEO_EJERCICIO);
        assertThat(ejercicioCreado.getHerramientas()).containsExactly(herramienta);
        assertThat(ejercicioCreado.getPasos()).containsExactly(paso);
    }

    @Test
    public void addEjercicio_WhenEjercicioYaExiste_ReturnThrowBadRequest(){
        var ejercicioDtoRequest = new EjercicioDtoRequest();
        ejercicioDtoRequest.setNombre(NOMBRE_EJERCICIO);
        ejercicioDtoRequest.setTipoEjercicio(TIPO_EJERCICIO);

        var tipoEjercicio = new TipoEjercicio();
        tipoEjercicio.setIdTipoEjercicio(ID_TIPO_EJERCICIO);
        tipoEjercicio.setNombre(TIPO_EJERCICIO);

        var ejercicio = new Ejercicio();

        when(tipoEjercicioService.getTipoEjercicioByNombre(TIPO_EJERCICIO)).thenReturn(tipoEjercicio);
        when(ejercicioRepository.findByTipoEjercicioAndNombreIgnoreCaseAndFechaBajaIsNull(tipoEjercicio, NOMBRE_EJERCICIO))
                .thenReturn(Optional.of(ejercicio));

        assertThatThrownBy(() -> ejercicioService.addEjercicio(ejercicioDtoRequest))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Ya existe un ejercicio con el nombre")
                .hasMessageContaining("del tipo")
                .hasMessageContaining(NOMBRE_EJERCICIO)
                .hasMessageContaining(TIPO_EJERCICIO)
                .extracting("status").isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void updateEjercicioById_WhenOk_ThenUpdateEjercicio(){
        var nombreEjercicioCambiado = NOMBRE_EJERCICIO.concat(" Cambiado");

        var ejercicioDtoRequest = new EjercicioDtoRequest();
        ejercicioDtoRequest.setNombre(nombreEjercicioCambiado);
        ejercicioDtoRequest.setTipoEjercicio(TIPO_EJERCICIO);
        ejercicioDtoRequest.setIdHerramientaList(List.of(ID_HERRAMIENTA));
        ejercicioDtoRequest.setPasos(List.of(new PasoDto()));
        ejercicioDtoRequest.setVideo(VIDEO_EJERCICIO);

        var tipoEjercicio = new TipoEjercicio();
        tipoEjercicio.setIdTipoEjercicio(ID_TIPO_EJERCICIO);

        var ejercicio = new Ejercicio();
        ejercicio.setNombre(NOMBRE_EJERCICIO);
        ejercicio.setTipoEjercicio(new TipoEjercicio());

        var herramienta = new Herramienta();
        herramienta.setIdHerramienta(ID_HERRAMIENTA);

        when(ejercicioRepository.findById(ID_EJERCICIO)).thenReturn(Optional.of(ejercicio));
        when(tipoEjercicioService.getTipoEjercicioByNombre(TIPO_EJERCICIO)).thenReturn(tipoEjercicio);
        when(ejercicioRepository.findByTipoEjercicioAndNombreIgnoreCaseAndFechaBajaIsNull(tipoEjercicio, nombreEjercicioCambiado))
                .thenReturn(Optional.empty());
        when(herramientaService.getHerramientasByIds(ejercicioDtoRequest.getIdHerramientaList()))
                .thenReturn(List.of(herramienta));

        ejercicioService.updateEjercicioById(ID_EJERCICIO, ejercicioDtoRequest);

        verify(pasoService).actualizarPasosEjercicio(ejercicioDtoRequest.getPasos(), ejercicio);
        verify(ejercicioRepository).save(ejercicioArgumentCaptor.capture());

        var ejercicioCapturado = ejercicioArgumentCaptor.getValue();

        assertThat(ejercicioCapturado.getNombre()).isEqualTo(nombreEjercicioCambiado);
        assertThat(ejercicioCapturado.getTipoEjercicio()).isEqualTo(tipoEjercicio);
        assertThat(ejercicioCapturado.getVideo()).isEqualTo(VIDEO_EJERCICIO);
        assertThat(ejercicioCapturado.getHerramientas()).containsExactly(herramienta);
    }

    @Test
    public void deleteEjercicioById_WhenOk_ThenDeleteEjercicio(){
        var ejercicio = new Ejercicio();

        when(ejercicioRepository.findById(ID_EJERCICIO)).thenReturn(Optional.of(ejercicio));

        ejercicioService.deleteEjercicioById(ID_EJERCICIO);

        verify(ejercicioRepository).delete(ejercicio);
    }
}