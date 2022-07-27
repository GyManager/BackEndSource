package org.gymanager.test.service.implementation;

import org.gymanager.model.domain.TipoEjercicio;
import org.gymanager.repository.specification.TipoEjercicioRepository;
import org.gymanager.service.implementation.TipoEjercicioServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.gymanager.test.constants.Constantes.TIPO_EJERCICIO;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TipoEjercicioServiceImplTest {

    @InjectMocks
    private TipoEjercicioServiceImpl tipoEjercicioService;

    @Mock
    private TipoEjercicioRepository tipoEjercicioRepository;

    @Test
    public void getTipoEjercicioByNombre_WhenOk_ThenReturnTipoEjercicio(){
        var tipoEjercicio = new TipoEjercicio();

        when(tipoEjercicioRepository.findByNombre(TIPO_EJERCICIO)).thenReturn(Optional.of(tipoEjercicio));

        var result = tipoEjercicioService.getTipoEjercicioByNombre(TIPO_EJERCICIO);

        assertThat(result).isEqualTo(tipoEjercicio);
    }

    @Test
    public void getTipoEjercicioByNombre_WhenNotExists_ThenThrowNotFound(){
        when(tipoEjercicioRepository.findByNombre(TIPO_EJERCICIO)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> tipoEjercicioService.getTipoEjercicioByNombre(TIPO_EJERCICIO))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Tipo ejercicio")
                .hasMessageContaining(TIPO_EJERCICIO)
                .hasMessageContaining("no encontrado")
                .extracting("status").isEqualTo(HttpStatus.NOT_FOUND);
    }
}