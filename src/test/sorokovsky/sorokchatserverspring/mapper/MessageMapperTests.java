package sorokovsky.sorokchatserverspring.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sorokovsky.sorokchatserverspring.mapper.MessageMapper;
import pro.sorokovsky.sorokchatserverspring.mapper.UserMapper;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mockStatic;
import static sorokovsky.sorokchatserverspring.util.MessagesUtil.*;
import static sorokovsky.sorokchatserverspring.util.TimeUtil.NOW_INSTANT;
import static sorokovsky.sorokchatserverspring.util.UsersUtil.getUserEntity;
import static sorokovsky.sorokchatserverspring.util.UsersUtil.getUserModel;

@ExtendWith(MockitoExtension.class)
public class MessageMapperTests {
    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private MessageMapper messageMapper;

    @Test
    public void toModel_fromEntity_success() {
        //given
        final var entity = getMessageEntity();
        final var expected = getMessageModel();
        doReturn(getUserModel()).when(userMapper).toModel(entity.getAuthor());

        //when
        final var mapped = messageMapper.toModel(entity);

        //then
        assertEquals(expected, mapped);
    }

    @Test
    public void toEntity_fromModel_success() {
        //given
        final var model = getMessageModel();
        final var expected = getMessageEntity();
        doReturn(getUserEntity()).when(userMapper).toEntity(model.getAuthor());

        //when
        final var mapped = messageMapper.toEntity(model);

        //then
        assertEquals(expected, mapped);
    }

    @Test
    public void merge_withText_success() {
        //given
        final var oldState = getMessageModel();
        final var newState = getNewStateWithText();
        final var expected = getMergedWithText();
        try (var mockedInstant = mockStatic(Instant.class)) {
            doReturn(getUserEntity()).when(userMapper).toEntity(oldState.getAuthor());
            mockedInstant.when(Instant::now).thenReturn(NOW_INSTANT);
            //when
            final var merged = messageMapper.merge(oldState, newState);

            //then
            assertEquals(expected, merged);
        }
    }

    @Test
    public void merge_withNullText_success() {
        //given
        final var oldState = getMessageModel();
        final var newState = getNewStateWithNullText();
        final var expected = getMergedWithNullText();
        try (var mockedInstant = mockStatic(Instant.class)) {
            doReturn(getUserEntity()).when(userMapper).toEntity(oldState.getAuthor());
            mockedInstant.when(Instant::now).thenReturn(NOW_INSTANT);
            //when
            final var merged = messageMapper.merge(oldState, newState);

            //then
            assertEquals(expected, merged);
        }
    }

    @Test
    public void merge_withEmptyText_success() {
        //given
        final var oldState = getMessageModel();
        final var newState = getNewStateWithEmptyText();
        final var expected = getMergedWithEmptyText();
        try (var mockedInstant = mockStatic(Instant.class)) {
            doReturn(getUserEntity()).when(userMapper).toEntity(oldState.getAuthor());
            mockedInstant.when(Instant::now).thenReturn(NOW_INSTANT);
            //when
            final var merged = messageMapper.merge(oldState, newState);

            //then
            assertEquals(expected, merged);
        }
    }

    @Test
    public void toModel_fromNewMessagePayload_success() {
        //given
        final var newMessagePayload = getNewMessagePayload();
        final var expected = getMessageEntity();
        final var user = getUserModel();
        try (final var mockedInstant = mockStatic(Instant.class)) {
            mockedInstant.when(Instant::now).thenReturn(NOW_INSTANT);
            doReturn(getUserEntity()).when(userMapper).toEntity(user);
            expected.setId(null);

            //when
            final var mapped = messageMapper.toEntity(newMessagePayload, user);

            //then
            assertEquals(expected, mapped);
        }
    }
}
