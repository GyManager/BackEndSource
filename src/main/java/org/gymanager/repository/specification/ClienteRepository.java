package org.gymanager.repository.specification;

import org.gymanager.model.domain.clientes.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

}
