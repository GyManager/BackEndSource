package org.gymanager.repository.specification;

import org.gymanager.model.domain.Herramienta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HerramientaRepository extends JpaRepository<Herramienta, Long> {
    List<Herramienta> findByEjerciciosIdEjercicio(Long idEjercicio);
}
