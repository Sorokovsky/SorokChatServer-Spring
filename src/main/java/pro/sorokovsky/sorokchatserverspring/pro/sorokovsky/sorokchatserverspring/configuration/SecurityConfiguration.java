package pro.sorokovsky.sorokchatserverspring.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import pro.sorokovsky.sorokchatserverspring.configurer.JwtConfigurer;
import pro.sorokovsky.sorokchatserverspring.deserializer.TokenDeserializer;
import pro.sorokovsky.sorokchatserverspring.factory.AccessTokenFactory;
import pro.sorokovsky.sorokchatserverspring.factory.RefreshTokenFactory;
import pro.sorokovsky.sorokchatserverspring.serializer.TokenSerializer;
import pro.sorokovsky.sorokchatserverspring.service.JwtAuthenticationUserDetailsService;
import pro.sorokovsky.sorokchatserverspring.storage.TokenStorage;
import pro.sorokovsky.sorokchatserverspring.strategy.JwtSessionStrategy;

import java.util.List;

@Configuration
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            JwtSessionStrategy jwtSessionStrategy,
            JwtConfigurer jwtConfigurer,
            PreAuthenticatedAuthenticationProvider preAuthenticatedAuthenticationProvider
    ) throws Exception {
        http
                .logout(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/authentication/register", "/authentication/login").anonymous()
                        .requestMatchers("/swagger-ui/**", "/openapi.yml/**").permitAll()
                        .requestMatchers("/authentication/**").authenticated()
                        .anyRequest().authenticated()
                )
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.NEVER)
                        .sessionAuthenticationStrategy(jwtSessionStrategy)
                )
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authenticationProvider(preAuthenticatedAuthenticationProvider);
        http.apply(jwtConfigurer);
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final var configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(List.of("*"));
        final var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
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
                .build();
    }

    @Bean
    public PreAuthenticatedAuthenticationProvider preAuthenticatedAuthenticationProvider(
            JwtAuthenticationUserDetailsService jwtAuthenticationUserDetailsService
    ) {
        final var provider = new PreAuthenticatedAuthenticationProvider();
        provider.setPreAuthenticatedUserDetailsService(jwtAuthenticationUserDetailsService);
        return provider;
    }
}
