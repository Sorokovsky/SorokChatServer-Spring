package sorokovsky.sorokchatserverspring.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sorokovsky.sorokchatserverspring.mapper.ChannelMapper;
import pro.sorokovsky.sorokchatserverspring.mapper.MessageMapper;
import pro.sorokovsky.sorokchatserverspring.mapper.UserMapper;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mockStatic;
import static sorokovsky.sorokchatserverspring.util.ChannelsUtil.*;
import static sorokovsky.sorokchatserverspring.util.MessagesUtil.getMessageEntity;
import static sorokovsky.sorokchatserverspring.util.MessagesUtil.getMessageModel;
import static sorokovsky.sorokchatserverspring.util.TimeUtil.NOW_INSTANT;
import static sorokovsky.sorokchatserverspring.util.UsersUtil.*;

@ExtendWith(MockitoExtension.class)
public class ChannelMapperTests {
    @Mock
    private UserMapper userMapper;

    @Mock
    private MessageMapper messageMapper;

    @InjectMocks
    private ChannelMapper channelMapper;

    @Test
    public void toModel_fromEntity_success() {
        //given
        final var entity = getChannelEntity();
        final var expected = getChannelModel();
        doReturn(getUserModel()).when(userMapper).toModel(getUserEntity());
        doReturn(getMessageModel()).when(messageMapper).toModel(getMessageEntity());

        //when
        final var mapped = channelMapper.toModel(entity);

        //then
        assertEquals(expected, mapped);
    }

    @Test
    public void toModel_fromNewChannel_success() {
        //given
        final var newChannel = getNewChannelPayload();
        final var expected = getChannelModel();
        expected.setId(null);
        expected.setUsers(List.of());
        expected.setMessages(List.of());
        try (final var mockedInstant = mockStatic(Instant.class)) {
            mockedInstant.when(Instant::now).thenReturn(NOW_INSTANT);

            //when
            final var mapped = channelMapper.toModel(newChannel);

            //then
            assertEquals(expected, mapped);
        }
    }

    @Test
    public void toGet_fromModel_success() {
        //given
        final var model = getChannelModel();
        final var expected = getChannelPayload();
        doReturn(getUserPayload()).when(userMapper).toGet(getUserModel());

        //when
        final var mapped = channelMapper.toGet(model);

        //then
        assertEquals(expected, mapped);
    }

    @Test
    public void toEntity_fromModel_success() {
        //given
        final var model = getChannelModel();
        final var expected = getChannelEntity();
        doReturn(getUserEntity()).when(userMapper).toEntity(getUserModel());
        doReturn(getMessageEntity()).when(messageMapper).toEntity(getMessageModel());

        //when
        final var mapped = channelMapper.toEntity(model);

        //then
        assertEquals(expected, mapped);
    }

    @Test
    public void merge_withData_success() {
        //given
        final var newState = getUpdateChannelWithDataPayload();
        final var expected = getUpdatedChannel();
        final var oldState = getChannelModel();
        try (final var mockedInstant = mockStatic(Instant.class)) {
            mockedInstant.when(Instant::now).thenReturn(NOW_INSTANT);

            //when
            final var mapped = channelMapper.merge(newState, oldState);

            //then
            assertEquals(expected, mapped);
        }
    }

    @Test
    public void merge_withIncorrectData_success() {
        //given
        final var newState = getUpdateChannelWithNotDataPayload();
        final var expected = getChannelModel();
        final var oldState = getChannelModel();
        try (final var mockedInstant = mockStatic(Instant.class)) {
            mockedInstant.when(Instant::now).thenReturn(NOW_INSTANT);

            //when
            final var mapped = channelMapper.merge(newState, oldState);

            //then
            assertEquals(expected, mapped);
        }
    }
}
