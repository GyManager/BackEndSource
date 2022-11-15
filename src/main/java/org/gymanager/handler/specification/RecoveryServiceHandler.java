package org.gymanager.handler.specification;

import org.gymanager.model.client.UsuarioRecovery;

public interface RecoveryServiceHandler {
    void resetPasswordUsuarioByMail(UsuarioRecovery usuarioRecovery);
}
