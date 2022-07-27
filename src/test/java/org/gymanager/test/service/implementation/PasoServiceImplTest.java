package org.gymanager.test.service.implementation;

import org.gymanager.converter.PasoEntityToDtoConverter;
import org.gymanager.model.client.PasoDto;
import org.gymanager.model.domain.Paso;
import org.gymanager.repository.specification.PasoRepository;
import org.gymanager.service.implementation.PasoServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.gymanager.test.constants.Constantes.ID_EJERCICIO;
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
}