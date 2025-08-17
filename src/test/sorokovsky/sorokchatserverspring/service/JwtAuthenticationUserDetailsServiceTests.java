package sorokovsky.sorokchatserverspring.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import pro.sorokovsky.sorokchatserverspring.contract.Token;
import pro.sorokovsky.sorokchatserverspring.service.JwtAuthenticationUserDetailsService;
import pro.sorokovsky.sorokchatserverspring.service.UsersService;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static sorokovsky.sorokchatserverspring.util.UsersUtil.getUserModel;

@ExtendWith(MockitoExtension.class)
public class JwtAuthenticationUserDetailsServiceTests {
    @Mock
    private UsersService usersService;

    @InjectMocks
    private JwtAuthenticationUserDetailsService service;

    @Test
    public void loadUserDetails_ifPrincipalIsNotToken_shouldThrowUsernameNotFoundException() {
        //given
        final var token = new PreAuthenticatedAuthenticationToken(new Object(), new Object());

        //when/then
        assertThrows(UsernameNotFoundException.class, () -> service.loadUserDetails(token));
    }

    @Test
    public void loadUserDetails_ifPrincipalIsToken_shouldReturnUser() {
        //given
        final var now = Instant.now();
        final var accessToken = new Token(UUID.randomUUID(), "user", now, now.plus(Duration.ofMinutes(15)));
        final var token = new PreAuthenticatedAuthenticationToken(accessToken, accessToken);
        final var user = getUserModel();
        doReturn(user).when(usersService).loadUserByUsername(accessToken.subject());

        //when
        final var result = service.loadUserDetails(token);

        //then
        assertEquals(user, result);
    }

    @Test
    public void loadUserDetails_ifPrincipalIsTokenButUserNotFound_shouldThrowUsernameNotFoundException() {
        //given
        final var now = Instant.now();
        final var accessToken = new Token(UUID.randomUUID(), "user", now, now.plus(Duration.ofMinutes(15)));
        final var token = new PreAuthenticatedAuthenticationToken(accessToken, accessToken);
        doThrow(UsernameNotFoundException.class).when(usersService).loadUserByUsername(accessToken.subject());

        //when/then
        assertThrows(UsernameNotFoundException.class, () -> service.loadUserDetails(token));
    }
}
