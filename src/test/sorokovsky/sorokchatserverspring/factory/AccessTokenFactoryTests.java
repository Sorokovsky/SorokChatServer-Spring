package sorokovsky.sorokchatserverspring.factory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pro.sorokovsky.sorokchatserverspring.contract.Token;
import pro.sorokovsky.sorokchatserverspring.factory.AccessTokenFactory;
import pro.sorokovsky.sorokchatserverspring.factory.DefaultAccessTokenFactory;

import java.time.Duration;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static sorokovsky.sorokchatserverspring.util.TimeUtil.NOW_INSTANT;

public class AccessTokenFactoryTests {
    private static final UUID ID = UUID.randomUUID();

    private AccessTokenFactory accessTokenFactory;
    private Duration lifetime;

    @BeforeEach
    public void setUp() {
        lifetime = Duration.ofMinutes(15);
        accessTokenFactory = new DefaultAccessTokenFactory(lifetime);
    }

    @Test
    public void shouldCreateAccessToken_fromRefresh_success() {
        //given
        final var refreshToken = new Token(ID, "username", NOW_INSTANT, NOW_INSTANT.plus(Duration.ofDays(7)));
        final var expected = new Token(ID, "username", NOW_INSTANT, NOW_INSTANT.plus(lifetime));
        try (final var mockedId = Mockito.mockStatic(UUID.class)) {
            mockedId.when(UUID::randomUUID).thenReturn(ID);

            //when
            final var accessToken = accessTokenFactory.apply(refreshToken);

            //then
            assertEquals(expected.id(), accessToken.id());
            assertEquals(expected.subject(), accessToken.subject());
        }
    }

    @Test
    public void shouldCorrectSetLifetime() {
        //given
        final var expected = new DefaultAccessTokenFactory();

        //when
        final var factory = expected.lifetime(Duration.ofMinutes(7));

        //then
        assertEquals(expected, factory);
    }
}
