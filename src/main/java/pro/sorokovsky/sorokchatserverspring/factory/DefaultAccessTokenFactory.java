package pro.sorokovsky.sorokchatserverspring.factory;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import pro.sorokovsky.sorokchatserverspring.constants.SecurityConstants;
import pro.sorokovsky.sorokchatserverspring.contract.Token;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
public class DefaultAccessTokenFactory implements AccessTokenFactory {
    private Duration lifetime = Duration.ofMinutes(15);

    public DefaultAccessTokenFactory lifetime(Duration lifetime) {
        this.lifetime = lifetime;
        return this;
    }

    @Override
    public Token apply(Token token) {
        final var now = Instant.now();
        final var authorities = List.of(SecurityConstants.ACCESS_TOKEN.name(), SecurityConstants.LOGOUT.name());
        return new Token(UUID.randomUUID(), token.subject(), authorities, now, now.plus(lifetime));
    }
}
