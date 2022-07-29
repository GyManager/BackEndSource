package org.gymanager.test.service.implementation;

import org.gymanager.converter.PasoEntityToDtoConverter;
import org.gymanager.model.client.PasoDto;
import org.gymanager.model.domain.Ejercicio;
import org.gymanager.model.domain.Paso;
import org.gymanager.repository.specification.PasoRepository;
import org.gymanager.service.implementation.PasoServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.gymanager.test.constants.Constantes.CONTENIDO_PASO;
import static org.gymanager.test.constants.Constantes.ID_EJERCICIO;
import static org.gymanager.test.constants.Constantes.ID_PASO;
import static org.gymanager.test.constants.Constantes.IMAGEN_PASO;
import static org.gymanager.test.constants.Constantes.NUMERO_PASO;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PasoServiceImplTest {

    @InjectMocks
    private PasoServiceImpl pasoService;

    @Mock
    private PasoRepository pasoRepository;

    @Mock
    private PasoEntityToDtoConverter pasoEntityToDtoConverter;

    @Test
    public void getPasosByIdEjercicio_WhenOk_ThenReturnPasos(){
        var paso = mock(Paso.class);
        var pasoDto = mock(PasoDto.class);

        when(pasoRepository.findByEjercicioIdEjercicio(ID_EJERCICIO)).thenReturn(List.of(paso));
        when(pasoEntityToDtoConverter.convert(List.of(paso))).thenReturn(List.of(pasoDto));

        var result = pasoService.getPasosByIdEjercicio(ID_EJERCICIO);

        assertThat(result).isNotNull();
        assertThat(result).containsExactly(pasoDto);
    }

    @Test
    public void crearPasos_WhenOk_ThenReturnNuevosPasos(){
        var pasoDto = new PasoDto();
        pasoDto.setNumeroPaso(NUMERO_PASO);
        pasoDto.setImagen(IMAGEN_PASO);
        pasoDto.setContenido(CONTENIDO_PASO);

        var result = pasoService.crearPasos(List.of(pasoDto));

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getNumeroPaso()).isEqualTo(NUMERO_PASO);
        assertThat(result.get(0).getImagen()).isEqualTo(IMAGEN_PASO);
        assertThat(result.get(0).getContenido()).isEqualTo(CONTENIDO_PASO);
    }

    @Test
    public void crearPasos_WhenNullList_ThenReturnEmptyList(){
        var result = pasoService.crearPasos(null);

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(0);
    }

    @Test
    public void crearPasos_WhenNumeroDePasoRepetido_ThenThrowBadRequest(){
        var pasoDtoUno = new PasoDto();
        pasoDtoUno.setNumeroPaso(NUMERO_PASO);

        var pasoDtoDos = new PasoDto();
        pasoDtoDos.setNumeroPaso(NUMERO_PASO);

        assertThatThrownBy(() -> pasoService.crearPasos(List.of(pasoDtoUno, pasoDtoDos)))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Hay pasos con numero de paso repetido, el numero de " +
                        "paso debe ser unico para poder mostrarlos en ese orden.")
                .extracting("status").isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void actualizarPasosEjercicio_WhenNuevosPasos_ThenCreateAndAdd(){
        var nuevoPasoDto = new PasoDto();
        nuevoPasoDto.setNumeroPaso(NUMERO_PASO);

        var pasoDtoList = List.of(nuevoPasoDto);
        var ejercicio = new Ejercicio();
        ejercicio.setPasos(new ArrayList<>());

        pasoService.actualizarPasosEjercicio(pasoDtoList, ejercicio);

        assertThat(ejercicio.getPasos().size()).isEqualTo(1);
        assertThat(ejercicio.getPasos().get(0).getNumeroPaso()).isEqualTo(NUMERO_PASO);
    }

    @Test
    public void actualizarPasosEjercicio_WhenPasosActualizados_ThenActualizarPasos(){
        var pasoDtoActualizado = new PasoDto();
        pasoDtoActualizado.setIdPaso(ID_PASO);
        pasoDtoActualizado.setNumeroPaso(NUMERO_PASO);

        var pasoDtoList = List.of(pasoDtoActualizado);

        var paso = new Paso();
        paso.setIdPaso(ID_PASO);

        var pasoList = new ArrayList<Paso>();
        pasoList.add(paso);

        var ejercicio = new Ejercicio();
        ejercicio.setPasos(pasoList);

        pasoService.actualizarPasosEjercicio(pasoDtoList, ejercicio);

        assertThat(ejercicio.getPasos().size()).isEqualTo(1);
        assertThat(ejercicio.getPasos().get(0).getIdPaso()).isEqualTo(ID_PASO);
        assertThat(ejercicio.getPasos().get(0).getNumeroPaso()).isEqualTo(NUMERO_PASO);
    }


    @Test
    public void actualizarPasosEjercicio_WhenPasosBorrados_ThenBorrarPasos(){
        var pasoDtoList = new ArrayList<PasoDto>();

        var paso = new Paso();
        paso.setIdPaso(ID_PASO);

        var pasoList = new ArrayList<Paso>();
        pasoList.add(paso);

        var ejercicio = new Ejercicio();
        ejercicio.setPasos(pasoList);

        pasoService.actualizarPasosEjercicio(pasoDtoList, ejercicio);

        assertThat(ejercicio.getPasos().isEmpty()).isTrue();
    }

    @Test
    public void actualizarPasosEjercicio_WhenCambios_ThenCambiar(){
        final var numeroPasoSiguiente = 10;
        final var numeroPasoBorrado = 100;
        final var idPasoBorrado = 1000L;

        var pasoDtoActualizado = new PasoDto();
        pasoDtoActualizado.setIdPaso(ID_PASO);
        pasoDtoActualizado.setNumeroPaso(NUMERO_PASO);
        var pasoAActualizar = new Paso();
        pasoAActualizar.setIdPaso(ID_PASO);

        var pasoDtoNuevo = new PasoDto();
        pasoDtoNuevo.setNumeroPaso(numeroPasoSiguiente);

        var pasoBorrado = new Paso();
        pasoBorrado.setIdPaso(idPasoBorrado);
        pasoBorrado.setNumeroPaso(numeroPasoBorrado);

        var pasoDtoList = List.of(pasoDtoActualizado, pasoDtoNuevo);
        var pasoList = new ArrayList<Paso>();
        pasoList.add(pasoAActualizar);
        pasoList.add(pasoBorrado);

        var ejercicio = new Ejercicio();
        ejercicio.setPasos(pasoList);

        pasoService.actualizarPasosEjercicio(pasoDtoList, ejercicio);

        assertThat(ejercicio.getPasos().size()).isEqualTo(2);
        assertThat(ejercicio.getPasos().contains(pasoAActualizar)).isTrue();
        assertThat(pasoAActualizar.getNumeroPaso()).isEqualTo(NUMERO_PASO);
        assertThat(ejercicio.getPasos().contains(pasoBorrado)).isFalse();
        var pasoNuevo = ejercicio.getPasos().stream().filter(paso -> Objects.isNull(paso.getIdPaso())).findFirst();
        assertThat(pasoNuevo.isPresent()).isTrue();
        assertThat(pasoNuevo.get().getNumeroPaso()).isEqualTo(numeroPasoSiguiente);
    }
}