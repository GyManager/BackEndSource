package org.gymanager.test.converter;

import org.gymanager.converter.EjercicioAplicadoEntityToDtoConverter;
import org.gymanager.model.domain.Bloque;
import org.gymanager.model.domain.Ejercicio;
import org.gymanager.model.domain.EjercicioAplicado;
import org.gymanager.model.domain.Rutina;
import org.gymanager.model.domain.TipoEjercicio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.gymanager.test.constants.Constantes.CARGA;
import static org.gymanager.test.constants.Constantes.ID_BLOQUE;
import static org.gymanager.test.constants.Constantes.ID_EJERCICIO;
import static org.gymanager.test.constants.Constantes.ID_EJERCICIO_APLICADO;
import static org.gymanager.test.constants.Constantes.ID_TIPO_EJERCICIO;
import static org.gymanager.test.constants.Constantes.NOMBRE_BLOQUE;
import static org.gymanager.test.constants.Constantes.NOMBRE_EJERCICIO;
import static org.gymanager.test.constants.Constantes.PAUSA_MACRO;
import static org.gymanager.test.constants.Constantes.PAUSA_MICRO;
import static org.gymanager.test.constants.Constantes.REPETICIONES;
import static org.gymanager.test.constants.Constantes.SERIES;
import static org.gymanager.test.constants.Constantes.TIEMPO;
import static org.gymanager.test.constants.Constantes.TIPO_EJERCICIO;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class EjercicioAplicadoEntityToDtoConverterTest {

    @InjectMocks
    private EjercicioAplicadoEntityToDtoConverter ejercicioAplicadoEntityToDtoConverter;

    @Test
    public void convert_WhenOk_ThenReturnEjercicioAplicadoDto(){
        var rutina = mock(Rutina.class);

        var tipoEjercicio = new TipoEjercicio();
        tipoEjercicio.setIdTipoEjercicio(ID_TIPO_EJERCICIO);
        tipoEjercicio.setNombre(TIPO_EJERCICIO);

        var ejercicio = new Ejercicio();
        ejercicio.setIdEjercicio(ID_EJERCICIO);
        ejercicio.setNombre(NOMBRE_EJERCICIO);
        ejercicio.setTipoEjercicio(tipoEjercicio);

        var bloque = new Bloque();
        bloque.setIdBloque(ID_BLOQUE);
        bloque.setNombre(NOMBRE_BLOQUE);

        var ejercicioAplicado = new EjercicioAplicado();
        ejercicioAplicado.setIdEjercicioAplicado(ID_EJERCICIO_APLICADO);

        ejercicioAplicado.setRutina(rutina);
        ejercicioAplicado.setEjercicio(ejercicio);
        ejercicioAplicado.setBloque(bloque);

        ejercicioAplicado.setSeries(SERIES);
        ejercicioAplicado.setRepeticiones(REPETICIONES);
        ejercicioAplicado.setPausaMacro(PAUSA_MACRO);
        ejercicioAplicado.setPausaMicro(PAUSA_MICRO);
        ejercicioAplicado.setTiempo(TIEMPO);
        ejercicioAplicado.setCarga(CARGA);
        ejercicioAplicado.setEsTemplate(Boolean.TRUE);


        var resultado = ejercicioAplicadoEntityToDtoConverter.convert(List.of(ejercicioAplicado)).get(0);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getIdEjercicioAplicado()).isEqualTo(ID_EJERCICIO_APLICADO);
        assertThat(resultado.getIdEjercicio()).isEqualTo(ID_EJERCICIO);
        assertThat(resultado.getNombreEjercicio()).isEqualTo(NOMBRE_EJERCICIO);
        assertThat(resultado.getTipoEjercicio()).isEqualTo(TIPO_EJERCICIO);
        assertThat(resultado.getBloque()).isEqualTo(NOMBRE_BLOQUE);
        assertThat(resultado.getSeries()).isEqualTo(SERIES);
        assertThat(resultado.getRepeticiones()).isEqualTo(REPETICIONES);
        assertThat(resultado.getPausaMacro()).isEqualTo(PAUSA_MACRO);
        assertThat(resultado.getPausaMicro()).isEqualTo(PAUSA_MICRO);
        assertThat(resultado.getCarga()).isEqualTo(CARGA);
        assertThat(resultado.getTiempo()).isEqualTo(TIEMPO);
        assertThat(resultado.getEsTemplate()).isTrue();
    }

    @Test
    public void convert_WhenNestedNulls_ThenReturnWithNulls(){
        var ejercicioAplicado = new EjercicioAplicado();
        ejercicioAplicado.setIdEjercicioAplicado(ID_EJERCICIO_APLICADO);
        ejercicioAplicado.setSeries(SERIES);
        ejercicioAplicado.setRepeticiones(REPETICIONES);
        ejercicioAplicado.setPausaMacro(PAUSA_MACRO);
        ejercicioAplicado.setPausaMicro(PAUSA_MICRO);
        ejercicioAplicado.setTiempo(TIEMPO);
        ejercicioAplicado.setCarga(CARGA);
        ejercicioAplicado.setEsTemplate(Boolean.TRUE);


        var resultado = ejercicioAplicadoEntityToDtoConverter.convert(List.of(ejercicioAplicado)).get(0);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getIdEjercicioAplicado()).isEqualTo(ID_EJERCICIO_APLICADO);
        assertThat(resultado.getIdEjercicio()).isNull();
        assertThat(resultado.getNombreEjercicio()).isNull();
        assertThat(resultado.getTipoEjercicio()).isNull();
        assertThat(resultado.getBloque()).isNull();
        assertThat(resultado.getSeries()).isEqualTo(SERIES);
        assertThat(resultado.getRepeticiones()).isEqualTo(REPETICIONES);
        assertThat(resultado.getPausaMacro()).isEqualTo(PAUSA_MACRO);
        assertThat(resultado.getPausaMicro()).isEqualTo(PAUSA_MICRO);
        assertThat(resultado.getCarga()).isEqualTo(CARGA);
        assertThat(resultado.getTiempo()).isEqualTo(TIEMPO);
        assertThat(resultado.getEsTemplate()).isTrue();
    }

    @Test
    public void convert_WhenNull_ThenNull(){
        List<EjercicioAplicado> ejercicioAplicadoList = null;
        var resultado = ejercicioAplicadoEntityToDtoConverter.convert(ejercicioAplicadoList);

        assertThat(resultado).isNull();
    }
}