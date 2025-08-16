package sorokovsky.sorokchatserverspring.factory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import pro.sorokovsky.sorokchatserverspring.contract.Token;
import pro.sorokovsky.sorokchatserverspring.factory.DefaultRefreshTokenFactory;
import pro.sorokovsky.sorokchatserverspring.factory.RefreshTokenFactory;

import java.time.Duration;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mockStatic;
import static sorokovsky.sorokchatserverspring.util.TimeUtil.NOW_INSTANT;

public class RefreshTokenFactoryTests {
    private static final UUID ID = UUID.randomUUID();

    private RefreshTokenFactory refreshTokenFactory;
    private Duration lifetime;

    @BeforeEach
    public void beforeAll() {
        lifetime = Duration.ofDays(7);
        refreshTokenFactory = new DefaultRefreshTokenFactory(lifetime);
    }

    @Test
    public void shouldCreateRefreshToken() {
        //given
        final var authentication = new UsernamePasswordAuthenticationToken("username", "password");
        final var expected = new Token(ID, authentication.getName(), NOW_INSTANT, NOW_INSTANT.plus(lifetime));
        try (final var mockedId = mockStatic(UUID.class)) {
            mockedId.when(UUID::randomUUID).thenReturn(ID);

            //when
            final var token = refreshTokenFactory.apply(authentication);

            //then
            assertEquals(expected.subject(), token.subject());
            assertEquals(expected.id(), token.id());
        }
    }

    @Test
    public void shouldCorrectSetLifetime() {
        //given
        final var expected = new DefaultRefreshTokenFactory(Duration.ofDays(5));

        //when
        final var newRefreshTokenFactory = expected.lifetime(Duration.ofDays(3));

        //then
        assertEquals(expected, newRefreshTokenFactory);
    }
}
