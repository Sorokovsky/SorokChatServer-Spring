package sorokovsky.sorokchatserverspring.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sorokovsky.sorokchatserverspring.exception.channel.ChannelNotFoundException;
import pro.sorokovsky.sorokchatserverspring.exception.user.UserNotFoundException;
import pro.sorokovsky.sorokchatserverspring.mapper.ChannelMapper;
import pro.sorokovsky.sorokchatserverspring.repository.ChannelsRepository;
import pro.sorokovsky.sorokchatserverspring.service.ChannelsService;
import pro.sorokovsky.sorokchatserverspring.service.UsersService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.reset;
import static sorokovsky.sorokchatserverspring.util.ChannelsUtil.*;
import static sorokovsky.sorokchatserverspring.util.UsersUtil.getUserModel;

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

    @BeforeEach
    public void setUp() {
        reset(repository);
    }

    @Test
    public void getById_ifContainsChannelById_shouldReturnChannelById() {
        //given
        final Long id = 1L;
        final var expectedModel = getChannelModel();
        final var expected = Optional.of(expectedModel);
        final var entity = getChannelEntity();
        final var optionalEntity = Optional.of(entity);
        doReturn(optionalEntity).when(repository).findById(id);
        doReturn(expectedModel).when(mapper).toModel(entity);

        //when
        final var result = service.getById(id);

        //then
        assertTrue(result.isPresent());
        assertEquals(expected.orElse(null), result.orElse(null));
    }

    @Test
    public void getById_ifNotContainsChannelById_shouldReturnEmptyOptional() {
        //given
        final Long id = 2L;
        doReturn(Optional.empty()).when(repository).findById(id);

        //when
        final var result = service.getById(id);

        //then
        assertTrue(result.isEmpty());
    }

    @Test
    public void getByUserId_ifContainsUserAndHaveChannels_shouldReturnChannels() {
        //given
        final Long userId = 1L;
        final var channelModel = getChannelModel();
        final var channelEntity = getChannelEntity();
        final var channelEntityList = List.of(channelEntity);
        final var expected = List.of(channelModel);
        doReturn(channelModel).when(mapper).toModel(channelEntity);
        doReturn(channelEntityList).when(repository).findAllByUsersId(userId);

        //when
        final var result = service.getByUserId(userId);

        //then
        assertFalse(result.isEmpty());
        assertEquals(expected.size(), result.size());
        assertEquals(expected, result);
    }

    @Test
    public void getByUserId_ifNotContainsUser_shouldReturnEmptyList() {
        //given
        final Long userId = 2L;
        doReturn(List.of()).when(repository).findAllByUsersId(userId);

        //when
        final var result = service.getByUserId(userId);

        //then
        assertTrue(result.isEmpty());
    }

    @Test
    public void create_shouldReturnCorrectModel() {
        //given
        final var user = getUserModel();
        final var newChannel = getNewChannelPayload();
        final var channelModelWithoutUsers = getChannelWithoutUsers();
        final var channelEntity = getChannelEntity();
        final var expected = getChannelModel();
        doReturn(channelModelWithoutUsers).when(mapper).toModel(newChannel);
        doReturn(channelEntity).when(repository).save(channelEntity);
        doReturn(channelEntity).when(mapper).toEntity(expected);
        doReturn(expected).when(mapper).toModel(channelEntity);

        //when
        final var result = service.create(user, newChannel);

        //then
        assertEquals(expected, result);
    }

    @Test
    public void update_ifNotFoundChannel_shouldThrowChannelNotFoundException() {
        //given
        final Long id = 2L;
        final var updated = getUpdateChannelWithDataPayload();
        doReturn(Optional.empty()).when(repository).findById(id);

        //when/then
        assertThrows(ChannelNotFoundException.class, () -> service.update(id, updated));
    }

    @Test
    public void update_ifFoundChannel_shouldUpdateChannel() {
        //given
        final Long id = 1L;
        final var updatePayload = getUpdateChannelWithDataPayload();
        final var channelEntity = getChannelEntity();
        final var channelModel = getChannelModel();
        final var merged = getUpdatedChannel();
        final var mergedEntity = getUpdatedChannelEntity();
        doReturn(Optional.of(channelEntity)).when(repository).findById(id);
        doReturn(channelModel).when(mapper).toModel(channelEntity);
        doReturn(merged).when(mapper).merge(updatePayload, channelModel);
        doReturn(mergedEntity).when(mapper).toEntity(merged);
        doReturn(mergedEntity).when(repository).save(mergedEntity);
        doReturn(merged).when(mapper).toModel(mergedEntity);

        //when
        final var result = service.update(id, updatePayload);

        //then
        assertEquals(merged, result);
    }

    @Test
    public void removeUser_ifNotFoundChannel_shouldThrowChannelNotFoundException() {
        //given
        final long userId = 1L;
        final Long id = 2L;
        doReturn(Optional.empty()).when(repository).findById(id);

        //when/given
        assertThrows(ChannelNotFoundException.class, () -> service.removeUser(id, userId));
    }

    @Test
    public void removeUser_ifFoundChannel_shouldRemoveUserFromChannel() {
        //given
        final Long id = 1L;
        final long userId = 1L;
        final var channelEntity = getChannelEntity();
        final var channelModel = getChannelModel();
        final var channelWithoutUsers = getChannelWithoutUsers();
        final var channelEntityWithoutUsers = getChannelEntityWithoutUsers();
        doReturn(Optional.of(channelEntity)).when(repository).findById(id);
        doReturn(channelModel).when(mapper).toModel(channelEntity);
        doReturn(channelEntityWithoutUsers).when(mapper).toEntity(channelWithoutUsers);
        doReturn(channelEntityWithoutUsers).when(repository).save(channelEntityWithoutUsers);
        doReturn(channelWithoutUsers).when(mapper).toModel(channelEntityWithoutUsers);

        //when
        final var result = service.removeUser(id, userId);

        //then
        assertEquals(channelWithoutUsers, result);
    }

    @Test
    public void addUser_ifNotFoundChannel_shouldThrowChannelNotFoundException() {
        //given
        final Long id = 1L;
        final var userId = 2L;
        doReturn(Optional.empty()).when(repository).findById(id);

        //when/then
        assertThrows(ChannelNotFoundException.class, () -> service.addUser(id, userId));
    }

    @Test
    public void addUser_ifNotFoundIUser_shouldThrowUserNotFoundException() {
        //given
        final Long id = 1L;
        final long userId = 2L;
        final var channelEntity = getChannelEntity();
        doReturn(Optional.empty()).when(usersService).getById(userId);
        doReturn(Optional.of(channelEntity)).when(repository).findById(id);
        doReturn(getChannelModel()).when(mapper).toModel(channelEntity);

        //when/then
        assertThrows(UserNotFoundException.class, () -> service.addUser(id, userId));
    }

    @Test
    public void addUser_ifFoundChannelAndIfFoundUser_shouldAddUser() {
        //given
        final Long id = 1L;
        final long userId = 2L;
        final var userModel = getUserModel();
        final var channelEntityWithoutUsers = getChannelEntityWithoutUsers();
        final var channelModelWithoutUsers = getChannelWithoutUsers();
        final var channelEntity = getChannelEntity();
        final var channelModel = getChannelModel();
        doReturn(Optional.of(userModel)).when(usersService).getById(userId);
        doReturn(Optional.of(channelEntityWithoutUsers)).when(repository).findById(id);
        doReturn(channelModelWithoutUsers).when(mapper).toModel(channelEntityWithoutUsers);
        doReturn(channelEntity).when(mapper).toEntity(channelModel);
        doReturn(channelEntity).when(repository).save(channelEntity);
        doReturn(channelModel).when(mapper).toModel(channelEntity);

        //when
        final var result = service.addUser(id, userId);

        //then
        assertEquals(channelModel, result);
    }

    @Test
    public void delete_ifNotFoundChannel_shouldThrowChannelNotFoundException() {
        //given
        final Long id = 1L;
        doReturn(Optional.empty()).when(repository).findById(id);

        //when/then
        assertThrows(ChannelNotFoundException.class, () -> service.delete(id));
    }

    @Test
    public void delete_ifFoundChannel_shouldDeleteChannel() {
        //given
        final Long id = 1L;
        final var channelEntity = getChannelEntity();
        final var channelModel = getChannelModel();
        doReturn(Optional.of(channelEntity)).when(repository).findById(id);
        doReturn(channelModel).when(mapper).toModel(channelEntity);

        //when
        final var result = service.delete(id);

        //then
        assertEquals(channelModel, result);
    }
}
