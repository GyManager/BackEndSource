package org.gymanager.repository.specification;

import org.gymanager.model.domain.TipoDocumento;
import org.gymanager.model.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>, JpaSpecificationExecutor<Usuario> {

    Optional<Usuario> findByMail(String mail);

    Optional<Usuario> findByTipoDocumentoAndNumeroDocumento(TipoDocumento tipoDocumento, Long numeroDocumento);
}
