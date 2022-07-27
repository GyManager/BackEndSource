package org.gymanager.test.converter;

import org.gymanager.converter.EjercicioEntityToDtoConverter;
import org.gymanager.model.domain.Ejercicio;
import org.gymanager.model.domain.TipoEjercicio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.gymanager.test.constants.Constantes.ID_EJERCICIO;
import static org.gymanager.test.constants.Constantes.ID_TIPO_EJERCICIO;
import static org.gymanager.test.constants.Constantes.NOMBRE_EJERCICIO;
import static org.gymanager.test.constants.Constantes.TIPO_EJERCICIO;
import static org.gymanager.test.constants.Constantes.VIDEO_EJERCICIO;

@ExtendWith(MockitoExtension.class)
class EjercicioEntityToDtoConverterTest {

    @InjectMocks
    private EjercicioEntityToDtoConverter ejercicioEntityToDtoConverter;

    @Test
    public void convert_WhenOk_ThenReturnEjercicioDto(){
        var tipoEjercicio = new TipoEjercicio();
        tipoEjercicio.setIdTipoEjercicio(ID_TIPO_EJERCICIO);
        tipoEjercicio.setNombre(TIPO_EJERCICIO);

        var ejercicio = new Ejercicio();
        ejercicio.setIdEjercicio(ID_EJERCICIO);
        ejercicio.setTipoEjercicio(tipoEjercicio);
        ejercicio.setNombre(NOMBRE_EJERCICIO);
        ejercicio.setVideo(VIDEO_EJERCICIO);

        var result = ejercicioEntityToDtoConverter.convert(ejercicio);

        assertThat(result).isNotNull();
        assertThat(result.getIdEjercicio()).isEqualTo(ID_EJERCICIO);
        assertThat(result.getTipoEjercicio()).isEqualTo(TIPO_EJERCICIO);
        assertThat(result.getNombre()).isEqualTo(NOMBRE_EJERCICIO);
        assertThat(result.getVideo()).isEqualTo(VIDEO_EJERCICIO);
    }

    @Test
    public void convert_WhenNull_ThenNull(){
        List<Ejercicio> ejercicioList = null;
        var resultado = ejercicioEntityToDtoConverter.convert(ejercicioList);

        assertThat(resultado).isNull();
    }
}