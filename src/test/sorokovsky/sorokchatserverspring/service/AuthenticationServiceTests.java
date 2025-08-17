package sorokovsky.sorokchatserverspring.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import pro.sorokovsky.sorokchatserverspring.exception.authentication.InvalidCredentialsException;
import pro.sorokovsky.sorokchatserverspring.exception.user.UserAlreadyExistsException;
import pro.sorokovsky.sorokchatserverspring.service.AuthenticationService;
import pro.sorokovsky.sorokchatserverspring.service.UsersService;
import pro.sorokovsky.sorokchatserverspring.strategy.JwtSessionStrategy;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static sorokovsky.sorokchatserverspring.util.AuthenticationUtil.getLoginPayload;
import static sorokovsky.sorokchatserverspring.util.UsersUtil.getNewUserPayload;
import static sorokovsky.sorokchatserverspring.util.UsersUtil.getUserModel;

@ExtendWith(MockitoExtension.class)
public class
AuthenticationServiceTests {
    @Mock
    private UsersService usersService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtSessionStrategy sessionStrategy;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    public void register_success() {
        //given
        final var payload = getNewUserPayload();
        final var userModel = getUserModel();
        doReturn(userModel).when(usersService).create(payload);

        //when
        final var created = usersService.create(payload);

        //then
        assertEquals(userModel, created);
    }

    @Test
    public void register_ifUserAlreadyExists_throwsUserAlreadyExistsException() {
        //given
        final var payload = getNewUserPayload();
        doThrow(UserAlreadyExistsException.class).when(usersService).create(payload);

        //when/then
        assertThrows(UserAlreadyExistsException.class, () -> usersService.create(payload));
    }

    @Test
    public void login_ifEmailNotCorrect_throwsInvalidCredentialsException() {
        //given
        final var payload = getLoginPayload();
        doReturn(Optional.empty()).when(usersService).getByEmail(payload.email());

        //when/then
        assertThrows(InvalidCredentialsException.class, () -> authenticationService.login(payload));
    }

    @Test
    public void login_ifPasswordNotCorrect_throwsInvalidCredentialsException() {
        //given
        final var payload = getLoginPayload();
        final var userModel = getUserModel();
        doReturn(Optional.of(userModel)).when(usersService).getByEmail(payload.email());
        doReturn(false).when(passwordEncoder).matches(payload.password(), userModel.getPassword());

        //when/then
        assertThrows(InvalidCredentialsException.class, () -> authenticationService.login(payload));
    }

    @Test
    public void login_ifCorrectData_successful() {
        //given
        final var payload = getLoginPayload();
        final var userModel = getUserModel();
        doReturn(Optional.of(userModel)).when(usersService).getByEmail(payload.email());
        doReturn(true).when(passwordEncoder).matches(payload.password(), userModel.getPassword());

        //when
        final var result = authenticationService.login(payload);

        //then
        assertEquals(userModel, result);
    }
}
