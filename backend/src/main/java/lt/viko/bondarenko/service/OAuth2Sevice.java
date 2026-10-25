package lt.viko.bondarenko.service;

import lt.viko.bondarenko.dto.response.JwtDto;

public interface OAuth2Sevice {
    JwtDto exchangeOAuthTokenForJWT(String oauthToken);
}
