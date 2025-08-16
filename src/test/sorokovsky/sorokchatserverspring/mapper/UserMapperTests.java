package sorokovsky.sorokchatserverspring.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import pro.sorokovsky.sorokchatserverspring.mapper.UserMapper;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mockStatic;
import static sorokovsky.sorokchatserverspring.util.TimeUtil.NOW_INSTANT;
import static sorokovsky.sorokchatserverspring.util.UsersUtil.*;

@ExtendWith(MockitoExtension.class)
public class UserMapperTests {

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserMapper userMapper;

    @Test
    public void toModel_fromEntity_success() {
        //given
        final var entity = getUserEntity();
        final var expected = getUserModel();

        //when
        final var mappedModel = userMapper.toModel(entity);

        //then
        assertEquals(expected, mappedModel);
    }

    @Test
    public void toEntity_fromNewUserPayload_success() {
        //given
        final var newUserPayload = getNewUserPayload();
        final var expected = getUserEntity();
        expected.setId(null);
        doReturn("hashed password").when(passwordEncoder).encode(newUserPayload.password());
        try (var mockedInstant = mockStatic(Instant.class)) {
            mockedInstant.when(Instant::now).thenReturn(NOW_INSTANT);

            //when
            final var mappedEntity = userMapper.toEntity(newUserPayload);

            //then
            assertEquals(expected, mappedEntity);
        }
    }

    @Test
    public void toEntity_fromModel_success() {
        //given
        final var model = getUserModel();
        final var expected = getUserEntity();

        //when
        final var mappedEntity = userMapper.toEntity(model);

        //then
        assertEquals(expected, mappedEntity);
    }

    @Test
    public void toGet_fromModel_success() {
        //given
        final var model = getUserModel();
        final var expected = getUserPayload();

        //when
        final var mappedGetPayload = userMapper.toGet(model);

        //then
        assertEquals(expected, mappedGetPayload);
    }

    @Test
    public void merge_withPassword_success() {
        //given
        final var oldState = getUserModel();
        final var newState = getNewStateWithPassword();
        final var expected = getMergedWithPassword();
        try (var mockedInstant = mockStatic(Instant.class)) {
            mockedInstant.when(Instant::now).thenReturn(NOW_INSTANT);
            doReturn("new hashed password").when(passwordEncoder).encode(newState.password());

            //when
            final var merged = userMapper.merge(oldState, newState);

            //then
            assertEquals(expected, merged);
        }
    }

    @Test
    public void merge_withNullPassword_success() {
        //given
        final var oldState = getUserModel();
        final var newState = getNewStateWithNullPassword();
        final var expected = getMergedWithNullPassword();
        try (var mockedInstant = mockStatic(Instant.class)) {
            mockedInstant.when(Instant::now).thenReturn(NOW_INSTANT);

            //when
            final var merged = userMapper.merge(oldState, newState);

            //then
            assertEquals(expected, merged);
        }
    }

    @Test
    public void merge_withEmptyPassword_success() {
        //given
        final var oldState = getUserModel();
        final var newState = getNewStateWithEmptyPassword();
        final var expected = getMergedWithEmptyPassword();
        try (var mockedInstant = mockStatic(Instant.class)) {
            mockedInstant.when(Instant::now).thenReturn(NOW_INSTANT);

            //when
            final var merged = userMapper.merge(oldState, newState);

            //then
            assertEquals(expected, merged);
        }
    }
}
