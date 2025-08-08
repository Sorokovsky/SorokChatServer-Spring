package pro.sorokovsky.sorokchatserverspring.factory;

import org.springframework.security.core.Authentication;
import pro.sorokovsky.sorokchatserverspring.constants.SecurityConstants;
import pro.sorokovsky.sorokchatserverspring.contract.Token;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;
import java.util.stream.Collectors;

public class DefaultRefreshTokenFactory implements RefreshTokenFactory {
    private Duration lifetime = Duration.ofDays(7);

    public DefaultRefreshTokenFactory(Duration lifetime) {
        this.lifetime = lifetime;
    }

    public DefaultRefreshTokenFactory() {
    }

    public DefaultRefreshTokenFactory lifetime(Duration lifetime) {
        this.lifetime = lifetime;
        return this;
    }

    @Override
    public Token apply(Authentication authentication) {
        final var now = Instant.now();
        final var authorities = authentication
                .getAuthorities()
                .stream()
                .map(authority -> SecurityConstants.GRANT_.name() + authority.getAuthority())
                .collect(Collectors.toList());
        authorities.add(SecurityConstants.REFRESH_TOKEN.name());
        return new Token(UUID.randomUUID(), authentication.getName(), authorities, now, now.plus(lifetime));
    }
}
