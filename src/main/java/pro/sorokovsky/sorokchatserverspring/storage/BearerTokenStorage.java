package pro.sorokovsky.sorokchatserverspring.storage;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import pro.sorokovsky.sorokchatserverspring.constants.AuthenticationVariants;

import java.time.Instant;
import java.util.Optional;

public class BearerTokenStorage implements TokenStorage {
    private static final String BEARER_PREFIX = "%s ".formatted(AuthenticationVariants.Bearer.name());

    @Override
    public Optional<String> get(HttpServletRequest request) {
        final var authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorization == null || !authorization.startsWith(BEARER_PREFIX)) return Optional.empty();
        return Optional.of(authorization.substring(BEARER_PREFIX.length()));
    }

    @Override
    public void set(String token, HttpServletResponse response, Instant expirationTime) {
        response.setHeader(HttpHeaders.AUTHORIZATION, "%s %s".formatted(AuthenticationVariants.Bearer.name(), token));
    }

    @Override
    public void clear(HttpServletResponse response) {
        response.setHeader(HttpHeaders.AUTHORIZATION, "");
    }
}
