package sorokovsky.sorokchatserverspring.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sorokovsky.sorokchatserverspring.mapper.ChannelMapper;
import pro.sorokovsky.sorokchatserverspring.mapper.MessageMapper;
import pro.sorokovsky.sorokchatserverspring.mapper.UserMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static sorokovsky.sorokchatserverspring.util.ChannelsUtil.getChannelEntity;
import static sorokovsky.sorokchatserverspring.util.ChannelsUtil.getChannelModel;
import static sorokovsky.sorokchatserverspring.util.MessagesUtil.getMessageEntity;
import static sorokovsky.sorokchatserverspring.util.MessagesUtil.getMessageModel;
import static sorokovsky.sorokchatserverspring.util.UsersUtil.getUserEntity;
import static sorokovsky.sorokchatserverspring.util.UsersUtil.getUserModel;

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
}
