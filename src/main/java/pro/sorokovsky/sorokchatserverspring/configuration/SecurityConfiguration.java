package pro.sorokovsky.sorokchatserverspring.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import pro.sorokovsky.sorokchatserverspring.factory.AccessTokenFactory;
import pro.sorokovsky.sorokchatserverspring.factory.RefreshTokenFactory;
import pro.sorokovsky.sorokchatserverspring.serializer.TokenSerializer;
import pro.sorokovsky.sorokchatserverspring.storage.TokenStorage;
import pro.sorokovsky.sorokchatserverspring.strategy.JwtSessionStrategy;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            JwtSessionStrategy jwtSessionStrategy
    ) throws Exception {
        http
                .authorizeHttpRequests(requests -> requests
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        .sessionAuthenticationStrategy(jwtSessionStrategy)
                );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtSessionStrategy jwtSessionStrategy(
            AccessTokenFactory accessTokenFactory,
            @Qualifier("jws-serializer")
            TokenSerializer accessTokenSerializer,
            @Qualifier("bearer-storage")
            TokenStorage accessTokenStorage,
            RefreshTokenFactory refreshTokenFactory,
            @Qualifier("jwe-serializer")
            TokenSerializer refreshTokenSerializer,
            @Qualifier("cookie-storage")
            TokenStorage refreshTokenStorage
    ) {
        return JwtSessionStrategy
                .builder()
                .accessTokenFactory(accessTokenFactory)
                .accessTokenSerializer(accessTokenSerializer)
                .accessTokenStorage(accessTokenStorage)
                .refreshTokenFactory(refreshTokenFactory)
                .refreshTokenSerializer(refreshTokenSerializer)
                .refreshTokenStorage(refreshTokenStorage)
                .build();
    }
}
