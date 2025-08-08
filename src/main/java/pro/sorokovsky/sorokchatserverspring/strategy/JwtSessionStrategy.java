package pro.sorokovsky.sorokchatserverspring.strategy;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import pro.sorokovsky.sorokchatserverspring.factory.AccessTokenFactory;
import pro.sorokovsky.sorokchatserverspring.factory.RefreshTokenFactory;
import pro.sorokovsky.sorokchatserverspring.serializer.TokenSerializer;
import pro.sorokovsky.sorokchatserverspring.storage.TokenStorage;

@RequiredArgsConstructor
public class JwtSessionStrategy implements SessionAuthenticationStrategy {
    private final TokenSerializer accessTokenSerializer;
    private final TokenSerializer refreshTokenSerializer;
    private final TokenStorage accessTokenStorage;
    private final TokenStorage refreshTokenStorage;
    private final AccessTokenFactory accessTokenFactory;
    private final RefreshTokenFactory refreshTokenFactory;

    @Override
    public void onAuthentication(
            Authentication authentication,
            HttpServletRequest request,
            HttpServletResponse response
    ) throws SessionAuthenticationException {
        final var refreshToken = refreshTokenFactory.apply(authentication);
        final var accessToken = accessTokenFactory.apply(refreshToken);
        accessTokenStorage.set(accessTokenSerializer.apply(accessToken), response, accessToken.expiresAt());
        refreshTokenStorage.set(refreshTokenSerializer.apply(refreshToken), response, refreshToken.expiresAt());
    }
}
