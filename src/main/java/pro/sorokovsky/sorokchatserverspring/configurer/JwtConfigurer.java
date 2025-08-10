package pro.sorokovsky.sorokchatserverspring.configurer;

import lombok.Builder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.csrf.CsrfFilter;
import pro.sorokovsky.sorokchatserverspring.convertor.JwtConverter;
import pro.sorokovsky.sorokchatserverspring.deserializer.TokenDeserializer;
import pro.sorokovsky.sorokchatserverspring.storage.TokenStorage;

@Builder
public class JwtConfigurer implements SecurityConfigurer<DefaultSecurityFilterChain, HttpSecurity> {
    private final TokenStorage accessTokenStorage;
    private final TokenDeserializer accessTokenDeserializer;

    @Override
    public void init(HttpSecurity builder) {

    }

    @Override
    public void configure(HttpSecurity builder) {
        final var authenticationManager = builder.getSharedObject(AuthenticationManager.class);
        final var converter = new JwtConverter(accessTokenStorage, accessTokenDeserializer);
        final var filter = new AuthenticationFilter(authenticationManager, converter);
        builder.addFilterBefore(filter, CsrfFilter.class);
    }
}
