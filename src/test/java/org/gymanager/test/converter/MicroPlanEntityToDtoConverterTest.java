package org.gymanager.test.converter;

import org.gymanager.converter.MicroPlanEntityToDtoConverter;
import org.gymanager.model.domain.MicroPlan;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.gymanager.test.constants.Constantes.ID_MICRO_PLAN;
import static org.gymanager.test.constants.Constantes.NOMBRE_MICRO_PLAN;

@ExtendWith(MockitoExtension.class)
class MicroPlanEntityToDtoConverterTest {

    @InjectMocks
    private MicroPlanEntityToDtoConverter microPlanEntityToDtoConverter;


    @Test
    public void convert_WhenOk_ThenReturnEjercicioAplicadoDto(){
        var microPlan = new MicroPlan();
        microPlan.setIdMicroPlan(ID_MICRO_PLAN);
        microPlan.setNombre(NOMBRE_MICRO_PLAN);
        microPlan.setNumeroOrden(1);
        microPlan.setEsTemplate(Boolean.TRUE);

        var resultado = microPlanEntityToDtoConverter.convert(List.of(microPlan)).get(0);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getIdMicroPlan()).isEqualTo(ID_MICRO_PLAN);
        assertThat(resultado.getNombre()).isEqualTo(NOMBRE_MICRO_PLAN);
        assertThat(resultado.getNumeroOrden()).isEqualTo(1);
        assertThat(resultado.getEsTemplate()).isTrue();
    }

    @Test
    public void convert_WhenNull_ThenNull(){
        List<MicroPlan> microPlans = null;
        var resultado = microPlanEntityToDtoConverter.convert(microPlans);

        assertThat(resultado).isNull();
    }

}