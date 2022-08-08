package org.gymanager.test.converter;

import org.gymanager.converter.RutinaEntityToDtoConverter;
import org.gymanager.model.domain.Rutina;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.gymanager.test.constants.Constantes.ID_RUTINA;
import static org.gymanager.test.constants.Constantes.NOMBRE_RUTINA;

@ExtendWith(MockitoExtension.class)
class RutinaEntityToDtoConverterTest {

    @InjectMocks
    private RutinaEntityToDtoConverter rutinaEntityToDtoConverter;

    @Test
    public void convert_WhenOk_ThenReturnEjercicioAplicadoDto(){
        var rutina = new Rutina();
        rutina.setIdRutina(ID_RUTINA);
        rutina.setNombre(NOMBRE_RUTINA);
        rutina.setEsTemplate(Boolean.TRUE);

        var resultado = rutinaEntityToDtoConverter.convert(List.of(rutina)).get(0);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getIdRutina()).isEqualTo(ID_RUTINA);
        assertThat(resultado.getNombre()).isEqualTo(NOMBRE_RUTINA);
        assertThat(resultado.getEsTemplate()).isTrue();
    }

    @Test
    public void convert_WhenNull_ThenNull(){
        List<Rutina> rutinas = null;
        var resultado = rutinaEntityToDtoConverter.convert(rutinas);

        assertThat(resultado).isNull();
    }
}