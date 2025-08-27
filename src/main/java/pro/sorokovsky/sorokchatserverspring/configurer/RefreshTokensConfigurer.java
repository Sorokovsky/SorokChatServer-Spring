package pro.sorokovsky.sorokchatserverspring.configurer;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFilter;
import pro.sorokovsky.sorokchatserverspring.deserializer.TokenDeserializer;
import pro.sorokovsky.sorokchatserverspring.factory.AccessTokenFactory;
import pro.sorokovsky.sorokchatserverspring.factory.RefreshTokenFactory;
import pro.sorokovsky.sorokchatserverspring.filter.RefreshTokensFilter;
import pro.sorokovsky.sorokchatserverspring.serializer.TokenSerializer;
import pro.sorokovsky.sorokchatserverspring.service.UsersService;
import pro.sorokovsky.sorokchatserverspring.storage.TokenStorage;

@Builder
@RequiredArgsConstructor
public class RefreshTokensConfigurer implements SecurityConfigurer<DefaultSecurityFilterChain, HttpSecurity> {
    private final TokenStorage accessTokenStorage;
    private final TokenStorage refreshTokenStorage;
    private final AccessTokenFactory accessTokenFactory;
    private final RefreshTokenFactory refreshTokenFactory;
    private final TokenSerializer accessTokenSerializer;
    private final TokenSerializer refreshTokenSerializer;
    private final TokenDeserializer refreshTokenDeserializer;
    private final UsersService usersService;

    @Override
    public void init(HttpSecurity builder) {

    }

    @Override
    public void configure(HttpSecurity builder) {
        final var filter = new RefreshTokensFilter(
                accessTokenStorage,
                refreshTokenStorage,
                accessTokenFactory,
                refreshTokenFactory,
                accessTokenSerializer,
                refreshTokenSerializer,
                refreshTokenDeserializer,
                usersService
        );

        builder.addFilterAfter(filter, AuthenticationFilter.class);
    }
}
