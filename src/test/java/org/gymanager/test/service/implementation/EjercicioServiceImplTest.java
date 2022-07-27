package org.gymanager.test.service.implementation;

import org.gymanager.converter.EjercicioEntityToDtoConverter;
import org.gymanager.model.client.EjercicioDto;
import org.gymanager.model.domain.Ejercicio;
import org.gymanager.model.enums.EjercicioSortBy;
import org.gymanager.repository.filters.EjercicioSpecification;
import org.gymanager.repository.specification.EjercicioRepository;
import org.gymanager.service.implementation.EjercicioServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EjercicioServiceImplTest {

    @InjectMocks
    private EjercicioServiceImpl ejercicioService;

    @Mock
    private EjercicioRepository ejercicioRepository;

    @Mock
    private EjercicioEntityToDtoConverter ejercicioEntityToDtoConverter;

    @Test
    public void getEjercicios_WhenOk_ThenReturnEjercicios(){
        var search = "filter";
        var page = 0;
        var pageSize = 20;
        var sortBy = EjercicioSortBy.NONE;
        var direction = Sort.Direction.ASC;

        var ejercicio = mock(Ejercicio.class);
        var ejercicioDto = mock(EjercicioDto.class);

        when(ejercicioRepository.findAll(any(EjercicioSpecification.class), any(PageRequest.class)))
                .thenReturn(new PageImpl<>(List.of(ejercicio)));
        when(ejercicioEntityToDtoConverter.convert(ejercicio)).thenReturn(ejercicioDto);

        var result = ejercicioService.getEjercicios(search, page, pageSize, sortBy, direction);

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
}