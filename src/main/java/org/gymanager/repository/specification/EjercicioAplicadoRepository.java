package org.gymanager.repository.specification;

import org.gymanager.model.domain.EjercicioAplicado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EjercicioAplicadoRepository extends JpaRepository<EjercicioAplicado, Long> {

    List<EjercicioAplicado> findByRutinaIdRutina(Long idRutina);
}
