package org.gymanager.test.service.implementation;

import org.gymanager.converter.HerramientaEntityToDtoConverter;
import org.gymanager.model.client.HerramientaDto;
import org.gymanager.model.domain.Herramienta;
import org.gymanager.repository.specification.HerramientaRepository;
import org.gymanager.service.implementation.HerramientaServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.gymanager.test.constants.Constantes.ID_EJERCICIO;
import static org.gymanager.test.constants.Constantes.ID_HERRAMIENTA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HerramientaServiceImplTest {

    @InjectMocks
    private HerramientaServiceImpl herramientaService;

    @Mock
    private HerramientaRepository herramientaRepository;

    @Mock
    private HerramientaEntityToDtoConverter herramientaEntityToDtoConverter;

    @Test
    public void getHerramientasByIdEjercicio_WhenOk_ThenReturnHerramientas(){
        var herramienta = mock(Herramienta.class);
        var herramientaDto = mock(HerramientaDto.class);

        when(herramientaRepository.findByEjerciciosIdEjercicio(ID_EJERCICIO)).thenReturn(List.of(herramienta));
        when(herramientaEntityToDtoConverter.convert(List.of(herramienta))).thenReturn(List.of(herramientaDto));

        var result = herramientaService.getHerramientasByIdEjercicio(ID_EJERCICIO);

        assertThat(result).isNotNull();
        assertThat(result).containsExactly(herramientaDto);
    }

    @Test
    public void getHerramientasByIds_WhenOk_ThenReturnHerramientas(){
        var idHerramientaList = List.of(ID_HERRAMIENTA);

        var herramienta = new Herramienta();
        herramienta.setIdHerramienta(ID_HERRAMIENTA);

        when(herramientaRepository.findAllById(idHerramientaList)).thenReturn(List.of(herramienta));

        var result = herramientaService.getHerramientasByIds(idHerramientaList);

        assertThat(result).isNotNull();
        assertThat(result).containsExactly(herramienta);
    }

    @Test
    public void getHerramientasByIds_WhenNullList_ThenReturnEmptyList(){
        var result = herramientaService.getHerramientasByIds(null);

        assertThat(result).isNotNull();
        assertThat(result.isEmpty()).isTrue();
    }

    @Test
    public void getHerramientas_WhenOk_ThenReturnHerramientas(){
        var herramienta = mock(Herramienta.class);
        var herramientaDto = mock(HerramientaDto.class);

        when(herramientaRepository.findAll()).thenReturn(List.of(herramienta));
        when(herramientaEntityToDtoConverter.convert(List.of(herramienta))).thenReturn(List.of(herramientaDto));

        var result = herramientaService.getHerramientas();

        assertThat(result).isNotNull();
        assertThat(result).containsExactly(herramientaDto);
    }
}