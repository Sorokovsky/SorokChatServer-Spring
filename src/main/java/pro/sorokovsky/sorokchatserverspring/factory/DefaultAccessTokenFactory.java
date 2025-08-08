package pro.sorokovsky.sorokchatserverspring.factory;

import pro.sorokovsky.sorokchatserverspring.constants.SecurityConstants;
import pro.sorokovsky.sorokchatserverspring.contract.Token;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;
import java.util.stream.Collectors;

public class DefaultAccessTokenFactory implements AccessTokenFactory {
    private Duration lifetime = Duration.ofMinutes(15);

    public DefaultAccessTokenFactory(Duration lifetime) {
        this.lifetime = lifetime;
    }

    public DefaultAccessTokenFactory() {
    }

    public DefaultAccessTokenFactory lifetime(Duration lifetime) {
        this.lifetime = lifetime;
        return this;
    }

    @Override
    public Token apply(Token token) {
        final var now = Instant.now();
        final var authorities = token.authorities()
                .stream()
                .filter(authority -> authority.startsWith(SecurityConstants.GRANT_.name()))
                .collect(Collectors.toList());
        authorities.add(SecurityConstants.ACCESS_TOKEN.name());
        return new Token(UUID.randomUUID(), token.subject(), authorities, now, now.plus(lifetime));
    }
}
