package org.gymanager.repository.specification;

import org.gymanager.model.domain.Objetivo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ObjetivoRepository extends JpaRepository<Objetivo, Long> {

    Optional<Objetivo> findByObjetivo(String objetivo);
}
