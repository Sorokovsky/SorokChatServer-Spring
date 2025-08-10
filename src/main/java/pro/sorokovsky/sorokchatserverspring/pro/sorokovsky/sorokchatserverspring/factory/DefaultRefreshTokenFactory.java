package pro.sorokovsky.sorokchatserverspring.factory;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import pro.sorokovsky.sorokchatserverspring.contract.Token;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
public class DefaultRefreshTokenFactory implements RefreshTokenFactory {
    private Duration lifetime = Duration.ofDays(7);

    public DefaultRefreshTokenFactory lifetime(Duration lifetime) {
        this.lifetime = lifetime;
        return this;
    }

    @Override
    public Token apply(Authentication authentication) {
        final var now = Instant.now();
        return new Token(UUID.randomUUID(), authentication.getName(), now, now.plus(lifetime));
    }
}
