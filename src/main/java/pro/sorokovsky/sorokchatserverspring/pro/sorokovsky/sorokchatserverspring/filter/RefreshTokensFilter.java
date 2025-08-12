package pro.sorokovsky.sorokchatserverspring.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import pro.sorokovsky.sorokchatserverspring.deserializer.TokenDeserializer;
import pro.sorokovsky.sorokchatserverspring.exception.InvalidTokenException;
import pro.sorokovsky.sorokchatserverspring.exception.TokenNotParsedException;
import pro.sorokovsky.sorokchatserverspring.factory.AccessTokenFactory;
import pro.sorokovsky.sorokchatserverspring.factory.RefreshTokenFactory;
import pro.sorokovsky.sorokchatserverspring.model.UserModel;
import pro.sorokovsky.sorokchatserverspring.serializer.TokenSerializer;
import pro.sorokovsky.sorokchatserverspring.storage.TokenStorage;

import java.io.IOException;

@RequiredArgsConstructor
public class RefreshTokensFilter extends OncePerRequestFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(RefreshTokensFilter.class);

    private final TokenStorage accessTokenStorage;
    private final TokenStorage refreshTokenStorage;
    private final AccessTokenFactory accessTokenFactory;
    private final RefreshTokenFactory refreshTokenFactory;
    private final TokenSerializer accessTokenSerializer;
    private final TokenSerializer refreshTokenSerializer;
    private final TokenDeserializer refreshTokenDeserializer;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserModel) {
            try {
                final var oldStringRefreshToken = refreshTokenDeserializer.apply(refreshTokenStorage.get(request).orElse(null));
                if (oldStringRefreshToken != null) {
                    final var newRefreshToken = refreshTokenFactory.apply(authentication);
                    final var newAccessToken = accessTokenFactory.apply(newRefreshToken);
                    accessTokenStorage.set(accessTokenSerializer.apply(newAccessToken), response, newAccessToken.expiresAt());
                    refreshTokenStorage.set(refreshTokenSerializer.apply(newRefreshToken), response, newRefreshToken.expiresAt());
                    LOGGER.info("Refresh token stored successfully");
                }

            } catch (TokenNotParsedException | InvalidTokenException exception) {
                LOGGER.info("Refresh token parsing error: {}", exception.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }
}
