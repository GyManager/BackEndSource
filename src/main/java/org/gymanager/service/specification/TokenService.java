package org.gymanager.service.specification;

import org.gymanager.model.client.usuarios.RolDto;
import org.gymanager.model.client.usuarios.UsuarioDto;
import org.gymanager.model.client.usuarios.token.UsuarioToken;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public interface TokenService {
    String generateAccessToken(String username, String issuer, List<String> authorities);

    String generateRefreshToken(String username, String issuer, List<String> authorities);

    String generateToken(String username, Long expiration, String issuer, List<String> authorities);

    UsuarioToken decodeJwtToken(String token);

    String getAccessTokenHeader();

    String getRefreshTokenHeader();

    String getTokenBearerPrefix();
}
