package org.gymanager.repository.specification;

import org.gymanager.model.domain.Paso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PasoRepository extends JpaRepository<Paso, Long> {
    List<Paso> findByEjercicioIdEjercicio(Long idEjercicio);
}
