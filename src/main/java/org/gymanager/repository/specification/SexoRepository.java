package org.gymanager.repository.specification;

import org.gymanager.model.domain.Sexo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SexoRepository extends JpaRepository<Sexo, Long> {

    Optional<Sexo> findBySexo(String sexo);
}
