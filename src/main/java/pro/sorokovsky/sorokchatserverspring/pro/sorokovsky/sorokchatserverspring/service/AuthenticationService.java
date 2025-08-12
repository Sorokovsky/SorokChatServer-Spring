package pro.sorokovsky.sorokchatserverspring.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;
import pro.sorokovsky.sorokchatserverspring.contract.LoginPayload;
import pro.sorokovsky.sorokchatserverspring.contract.NewUserPayload;
import pro.sorokovsky.sorokchatserverspring.exception.InvalidCredentialsException;
import pro.sorokovsky.sorokchatserverspring.model.UserModel;
import pro.sorokovsky.sorokchatserverspring.storage.TokenStorage;
import pro.sorokovsky.sorokchatserverspring.strategy.JwtSessionStrategy;

@Service
@RequestScope
public class AuthenticationService {
    private final UsersService usersService;
    private final JwtSessionStrategy sessionAuthenticationStrategy;
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final PasswordEncoder passwordEncoder;
    private final TokenStorage acceessTokenStorage;
    private final TokenStorage refreshTokenStorage;

    public AuthenticationService(
            UsersService usersService,
            JwtSessionStrategy sessionAuthenticationStrategy,
            HttpServletRequest request,
            HttpServletResponse response,
            PasswordEncoder passwordEncoder,
            @Qualifier("bearer-storage")
            TokenStorage acceessTokenStorage,
            @Qualifier("cookie-storage")
            TokenStorage refreshTokenStorage
    ) {
        this.usersService = usersService;
        this.sessionAuthenticationStrategy = sessionAuthenticationStrategy;
        this.request = request;
        this.response = response;
        this.passwordEncoder = passwordEncoder;
        this.acceessTokenStorage = acceessTokenStorage;
        this.refreshTokenStorage = refreshTokenStorage;
    }

    public UserModel register(NewUserPayload payload) {
        final var created = usersService.create(payload);
        authenticate(created);
        return created;
    }

    public UserModel login(LoginPayload payload) {
        final var candidate = usersService.getByEmail(payload.email())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid Email"));
        if (!passwordEncoder.matches(payload.password(), candidate.getPassword())) {
            throw new InvalidCredentialsException("Invalid Password");
        }
        authenticate(candidate);
        return candidate;
    }

    public void logout() {
        acceessTokenStorage.clear(response);
        refreshTokenStorage.clear(response);
    }

    private void authenticate(UserModel user) {
        final var authenticationToken = UsernamePasswordAuthenticationToken
                .authenticated(user.getEmail(), user.getPassword(), user.getAuthorities());
        sessionAuthenticationStrategy.onAuthentication(authenticationToken, request, response);
    }
}
