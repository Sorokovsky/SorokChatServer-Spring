package sorokovsky.sorokchatserverspring.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sorokovsky.sorokchatserverspring.exception.message.MessageNotFoundException;
import pro.sorokovsky.sorokchatserverspring.mapper.MessageMapper;
import pro.sorokovsky.sorokchatserverspring.repository.MessagesRepository;
import pro.sorokovsky.sorokchatserverspring.service.MessagesService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static sorokovsky.sorokchatserverspring.util.MessagesUtil.*;
import static sorokovsky.sorokchatserverspring.util.UsersUtil.getUserModel;

@ExtendWith(MockitoExtension.class)
public class MessagesServiceTests {
    @Mock
    private MessageMapper mapper;

    @Mock
    private MessagesRepository repository;

    @InjectMocks
    private MessagesService service;

    @Test
    public void getById_ifFoundMessage_returnsMessage() {
        //given
        final Long id = 1L;
        final var messageModel = getMessageModel();
        final var messageEntity = getMessageEntity();
        final var expected = Optional.of(messageModel);
        doReturn(Optional.of(messageEntity)).when(repository).findById(id);
        doReturn(messageModel).when(mapper).toModel(messageEntity);

        //when
        final var result = service.getById(id);

        //then
        assertEquals(expected.orElse(null), result.orElse(null));
    }

    @Test
    public void getById_ifNotFoundMessage_returnsEmptyOptional() {
        //given
        final Long id = 1L;
        doReturn(Optional.empty()).when(repository).findById(id);

        //when
        final var result = service.getById(id);

        //then
        assertTrue(result.isEmpty());
    }

    @Test
    public void create_successful() {
        //given
        final var userModel = getUserModel();
        final var newMessage = getNewMessagePayload();
        final var messageEntity = getMessageEntity();
        final var messageModel = getMessageModel();
        doReturn(messageEntity).when(mapper).toEntity(newMessage, userModel);
        doReturn(messageEntity).when(repository).save(messageEntity);
        doReturn(messageModel).when(mapper).toModel(messageEntity);

        //when
        final var result = service.create(userModel, newMessage);

        //then
        assertEquals(messageModel, result);
    }

    @Test
    public void update_ifNotFoundMessage_shouldThrowMessageNotFoundException() {
        //given
        final Long id = 1L;
        final var newStateWithText = getNewStateWithText();
        doReturn(Optional.empty()).when(repository).findById(id);

        //when/then
        assertThrows(MessageNotFoundException.class, () -> service.update(id, newStateWithText));
    }

    @Test
    public void update_ifFoundMessage_returnsMessage() {
        //given
        final Long id = 1L;
        final var newStateWithText = getNewStateWithText();
        final var messageEntity = getMessageEntity();
        final var messageModel = getMessageModel();
        final var mergedEntity = getMergedWithText();
        final var mergedModel = getMergedModelWithText();
        doReturn(Optional.of(messageEntity)).when(repository).findById(id);
        doReturn(messageModel).when(mapper).toModel(messageEntity);
        doReturn(mergedEntity).when(mapper).merge(messageModel, newStateWithText);
        doReturn(mergedEntity).when(repository).save(mergedEntity);
        doReturn(mergedModel).when(mapper).toModel(mergedEntity);

        //when
        final var result = service.update(id, newStateWithText);

        //then
        assertEquals(mergedModel, result);
    }

    @Test
    public void delete_ifNotFoundMessage_shouldThrowMessageNotFoundException() {
        //given
        final Long id = 1L;
        doReturn(Optional.empty()).when(repository).findById(id);

        //when/then
        assertThrows(MessageNotFoundException.class, () -> service.delete(id));
    }

    @Test
    public void delete_ifFoundMessage_returnsMessage() {
        //given
        final Long id = 1L;
        final var messageEntity = getMessageEntity();
        final var messageModel = getMessageModel();
        doReturn(Optional.of(messageEntity)).when(repository).findById(id);
        doReturn(messageModel).when(mapper).toModel(messageEntity);

        //when
        final var result = service.delete(id);
        assertEquals(messageModel, result);
    }
}
