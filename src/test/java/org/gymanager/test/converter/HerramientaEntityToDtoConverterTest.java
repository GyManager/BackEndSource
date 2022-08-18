package org.gymanager.test.converter;

import org.gymanager.converter.HerramientaEntityToDtoConverter;
import org.gymanager.model.domain.Herramienta;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.gymanager.test.constants.Constantes.DESCRIPCION_HERRAMIENTA;
import static org.gymanager.test.constants.Constantes.ID_HERRAMIENTA;
import static org.gymanager.test.constants.Constantes.NOMBRE_HERRAMIENTA;

@ExtendWith(MockitoExtension.class)
class HerramientaEntityToDtoConverterTest {

    @InjectMocks
    private HerramientaEntityToDtoConverter herramientaEntityToDtoConverter;

    @Test
    public void convert_WhenOk_ThenReturnHerramientaDto(){
        var herramienta = new Herramienta();
        herramienta.setIdHerramienta(ID_HERRAMIENTA);
        herramienta.setDescripcion(DESCRIPCION_HERRAMIENTA);
        herramienta.setNombre(NOMBRE_HERRAMIENTA);

        var result = herramientaEntityToDtoConverter.convert(herramienta);

        assertThat(result).isNotNull();
        assertThat(result.getIdHerramienta()).isEqualTo(ID_HERRAMIENTA);
        assertThat(result.getDescripcion()).isEqualTo(DESCRIPCION_HERRAMIENTA);
        assertThat(result.getNombre()).isEqualTo(NOMBRE_HERRAMIENTA);
    }

    @Test
    public void convert_WhenNull_ThenNull(){
        List<Herramienta> herramientaList = null;
        var resultado = herramientaEntityToDtoConverter.convert(herramientaList);

        assertThat(resultado).isNull();
    }
}