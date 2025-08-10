package pro.sorokovsky.sorokchatserverspring.convertor;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import pro.sorokovsky.sorokchatserverspring.deserializer.TokenDeserializer;
import pro.sorokovsky.sorokchatserverspring.storage.TokenStorage;

@RequiredArgsConstructor
public class JwtConverter implements AuthenticationConverter {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtConverter.class);

    private final TokenStorage accessTokenStorage;
    private final TokenDeserializer accessTokenDeserializer;

    @Override
    public Authentication convert(HttpServletRequest request) {
        final var stringAccessToken = accessTokenStorage.get(request).orElse(null);
        if (stringAccessToken == null) {
            LOGGER.info("Access token not stored");
            return null;
        } else {
            final var accessToken = accessTokenDeserializer.apply(stringAccessToken);
            LOGGER.info("Access token: {}", accessToken);
            return new PreAuthenticatedAuthenticationToken(accessToken, stringAccessToken);
        }
    }
}
