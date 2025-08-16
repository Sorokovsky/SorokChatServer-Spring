package sorokovsky.sorokchatserverspring.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sorokovsky.sorokchatserverspring.mapper.ChannelMapper;
import pro.sorokovsky.sorokchatserverspring.repository.ChannelsRepository;
import pro.sorokovsky.sorokchatserverspring.service.ChannelsService;
import pro.sorokovsky.sorokchatserverspring.service.UsersService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static sorokovsky.sorokchatserverspring.util.ChannelsUtil.getChannelEntity;
import static sorokovsky.sorokchatserverspring.util.ChannelsUtil.getChannelModel;

@ExtendWith(MockitoExtension.class)
public class ChannelsServiceTests {
    @Mock
    private ChannelsRepository repository;

    @Mock
    private ChannelMapper mapper;

    @Mock
    private UsersService usersService;

    @InjectMocks
    private ChannelsService service;

    @Test
    public void byId_ifContainsChannelById_shouldReturnChannelById() {
        //given
        final var id = 1L;
        final var expectedModel = getChannelModel();
        final var expected = Optional.of(expectedModel);
        final var entity = getChannelEntity();
        final var optionalEntity = Optional.of(entity);
        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toModel(optionalEntity.get())).thenReturn(expectedModel);

        //when
        final var result = service.getById(id);

        //then
        assertTrue(result.isPresent());
        assertEquals(expected.orElse(null), result.orElse(null));
    }
}
