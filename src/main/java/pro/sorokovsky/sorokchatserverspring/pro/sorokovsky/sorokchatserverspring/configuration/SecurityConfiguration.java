package pro.sorokovsky.sorokchatserverspring.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import pro.sorokovsky.sorokchatserverspring.configurer.JwtConfigurer;
import pro.sorokovsky.sorokchatserverspring.configurer.RefreshTokensConfigurer;
import pro.sorokovsky.sorokchatserverspring.deserializer.TokenDeserializer;
import pro.sorokovsky.sorokchatserverspring.entrypoint.AccessTokenNotFoundAuthenticationEntryPoint;
import pro.sorokovsky.sorokchatserverspring.factory.AccessTokenFactory;
import pro.sorokovsky.sorokchatserverspring.factory.RefreshTokenFactory;
import pro.sorokovsky.sorokchatserverspring.serializer.TokenSerializer;
import pro.sorokovsky.sorokchatserverspring.service.JwtAuthenticationUserDetailsService;
import pro.sorokovsky.sorokchatserverspring.service.UsersService;
import pro.sorokovsky.sorokchatserverspring.storage.TokenStorage;
import pro.sorokovsky.sorokchatserverspring.strategy.JwtSessionStrategy;

@Configuration
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            JwtConfigurer jwtConfigurer,
            AuthenticationProvider authenticationProvider,
            RefreshTokensConfigurer refreshTokensConfigurer
    ) throws Exception {
        http
                .logout(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/authentication/register", "/authentication/login").anonymous()
                        .requestMatchers("/swagger-ui/**", "/openapi.yml/**").permitAll()
                        .anyRequest().authenticated()
                )
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.NEVER)
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(new AccessTokenNotFoundAuthenticationEntryPoint())
                )
                .authenticationProvider(authenticationProvider);
        http.apply(jwtConfigurer);
        http.apply(refreshTokensConfigurer);
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

    @Bean
    JwtConfigurer jwtConfigurer(
            @Qualifier("jws-deserializer")
            TokenDeserializer accessTokenDeserializer,
            @Qualifier("bearer-storage")
            TokenStorage accessTokenStorage
    ) {
        return JwtConfigurer
                .builder()
                .accessTokenDeserializer(accessTokenDeserializer)
                .accessTokenStorage(accessTokenStorage)
                .failedAuthenticationEntryPoint(new AccessTokenNotFoundAuthenticationEntryPoint())
                .build();
    }

    @Bean
    public RefreshTokensConfigurer refreshTokensConfigurer(
            @Qualifier("jwe-deserializer")
            TokenDeserializer refreshTokenDeserializer,
            AccessTokenFactory accessTokenFactory,
            @Qualifier("jws-serializer")
            TokenSerializer accessTokenSerializer,
            @Qualifier("bearer-storage")
            TokenStorage accessTokenStorage,
            RefreshTokenFactory refreshTokenFactory,
            @Qualifier("jwe-serializer")
            TokenSerializer refreshTokenSerializer,
            @Qualifier("cookie-storage")
            TokenStorage refreshTokenStorage,
            UsersService usersService
    ) {
        return RefreshTokensConfigurer
                .builder()
                .refreshTokenDeserializer(refreshTokenDeserializer)
                .accessTokenFactory(accessTokenFactory)
                .accessTokenSerializer(accessTokenSerializer)
                .accessTokenStorage(accessTokenStorage)
                .refreshTokenFactory(refreshTokenFactory)
                .refreshTokenSerializer(refreshTokenSerializer)
                .refreshTokenStorage(refreshTokenStorage)
                .usersService(usersService)
                .build();
    }

    @Bean
    public AuthenticationProvider preAuthenticatedAuthenticationProvider(
            JwtAuthenticationUserDetailsService jwtAuthenticationUserDetailsService
    ) {
        final var provider = new PreAuthenticatedAuthenticationProvider();
        provider.setPreAuthenticatedUserDetailsService(jwtAuthenticationUserDetailsService);
        return provider;
    }
}
