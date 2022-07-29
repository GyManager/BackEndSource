package org.gymanager.test.converter;

import org.gymanager.converter.PasoEntityToDtoConverter;
import org.gymanager.model.domain.Paso;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.gymanager.test.constants.Constantes.CONTENIDO_PASO;
import static org.gymanager.test.constants.Constantes.ID_PASO;
import static org.gymanager.test.constants.Constantes.IMAGEN_PASO;
import static org.gymanager.test.constants.Constantes.NUMERO_PASO;

@ExtendWith(MockitoExtension.class)
class PasoEntityToDtoConverterTest {

    @InjectMocks
    private PasoEntityToDtoConverter pasoEntityToDtoConverter;

    @Test
    public void convert_WhenOk_ThenReturnPasoDto(){
        var paso = new Paso();
        paso.setIdPaso(ID_PASO);
        paso.setNumeroPaso(NUMERO_PASO);
        paso.setContenido(CONTENIDO_PASO);
        paso.setImagen(IMAGEN_PASO);

        var result = pasoEntityToDtoConverter.convert(paso);

        assertThat(result).isNotNull();
        assertThat(result.getIdPaso()).isEqualTo(ID_PASO);
        assertThat(result.getNumeroPaso()).isEqualTo(NUMERO_PASO);
        assertThat(result.getContenido()).isEqualTo(CONTENIDO_PASO);
        assertThat(result.getImagen()).isEqualTo(IMAGEN_PASO);
    }

    @Test
    public void convert_WhenNull_ThenNull(){
        List<Paso> pasoList = null;
        var resultado = pasoEntityToDtoConverter.convert(pasoList);

        assertThat(resultado).isNull();
    }
}