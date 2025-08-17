package sorokovsky.sorokchatserverspring.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sorokovsky.sorokchatserverspring.exception.user.UserAlreadyExistsException;
import pro.sorokovsky.sorokchatserverspring.exception.user.UserNotFoundException;
import pro.sorokovsky.sorokchatserverspring.mapper.UserMapper;
import pro.sorokovsky.sorokchatserverspring.repository.UsersRepository;
import pro.sorokovsky.sorokchatserverspring.service.UsersService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static sorokovsky.sorokchatserverspring.util.UsersUtil.*;

@ExtendWith(MockitoExtension.class)
public class UsersServiceTests {
    @Mock
    private UsersRepository repository;

    @Mock
    private UserMapper mapper;

    @InjectMocks
    private UsersService service;

    @Test
    public void getById_ifNotFoundUser_returnsEmptyOptional() {
        //given
        final Long id = 1L;
        doReturn(Optional.empty()).when(repository).findById(id);

        //when
        final var result = service.getById(id);

        //then
        assertTrue(result.isEmpty());
    }

    @Test
    public void getById_ifFoundUser_returnsUser() {
        //given
        final Long id = 1L;
        final var userModel = getUserModel();
        final var userEntity = getUserEntity();
        doReturn(Optional.of(userEntity)).when(repository).findById(id);
        doReturn(userModel).when(mapper).toModel(userEntity);

        //when
        final var result = service.getById(id);

        //then
        assertEquals(userModel, result.orElse(null));
    }

    @Test
    public void getByEmail_ifNotFoundUser_returnsEmptyOptional() {
        //given
        final var email = "ssss";
        doReturn(Optional.empty()).when(repository).findByEmail(email);

        //when
        final var result = service.getByEmail(email);

        //then
        assertTrue(result.isEmpty());
    }

    @Test
    public void getByEmail_ifFoundUser_returnsUser() {
        //given
        final var email = "Sorokovskys@ukr.net";
        final var userEntity = getUserEntity();
        final var userModel = getUserModel();
        doReturn(Optional.of(userEntity)).when(repository).findByEmail(email);
        doReturn(userModel).when(mapper).toModel(userEntity);

        //when
        final var result = service.getByEmail(email);

        //then
        assertEquals(userModel, result.orElse(null));
    }

    @Test
    public void create_ifFoundUser_throwUserAlreadyExistsException() {
        //given
        final var newUserPayload = getNewUserPayload();
        final var userModel = getUserModel();
        final var userEntity = getUserEntity();
        doReturn(Optional.of(userEntity)).when(repository).findByEmail(newUserPayload.email());
        doReturn(userModel).when(mapper).toModel(userEntity);

        //when/then
        assertThrows(UserAlreadyExistsException.class, () -> service.create(newUserPayload));
    }

    @Test
    public void create_ifUserNotFound_successful() {
        //given
        final var newUserPayload = getNewUserPayload();
        final var userModel = getUserModel();
        final var userEntity = getUserEntity();
        doReturn(Optional.empty()).when(repository).findByEmail(newUserPayload.email());
        doReturn(userEntity).when(mapper).toEntity(newUserPayload);
        doReturn(userEntity).when(repository).save(userEntity);
        doReturn(userModel).when(mapper).toModel(userEntity);

        //when
        final var result = service.create(newUserPayload);

        //then
        assertEquals(userModel, result);
    }

    @Test
    public void update_ifNotFoundUser_throwUserNotFoundException() {
        //given
        final var updateUserPayload = getNewStateWithPassword();
        final Long id = 1L;
        doReturn(Optional.empty()).when(repository).findById(id);

        //when/then
        assertThrows(UserNotFoundException.class, () -> service.update(id, updateUserPayload));
    }

    @Test
    public void update_ifFoundUser_successful() {
        //given
        final var updateUserPayload = getNewStateWithPassword();
        final var userModel = getUserModel();
        final var userEntity = getUserEntity();
        final var updatedEntity = getMergedWithPassword();
        final var updatedModel = getMergedModelWithPassword();
        doReturn(Optional.of(userEntity)).when(repository).findById(updatedModel.getId());
        doReturn(userModel).when(mapper).toModel(userEntity);
        doReturn(updatedEntity).when(mapper).merge(userModel, updateUserPayload);
        doReturn(updatedEntity).when(repository).save(updatedEntity);
        doReturn(updatedModel).when(mapper).toModel(updatedEntity);

        //when
        final var result = service.update(userModel.getId(), updateUserPayload);

        //then
        assertEquals(updatedModel, result);
    }

    @Test
    public void delete_ifNotFoundUser_throwUserNotFoundException() {
        //given
        final var id = 1L;
        doReturn(Optional.empty()).when(repository).findById(id);

        //when/then
        assertThrows(UserNotFoundException.class, () -> service.delete(id));
    }

    @Test
    public void delete_ifFoundUser_successful() {
        //given
        final var userEntity = getUserEntity();
        final var userModel = getUserModel();
        doReturn(Optional.of(userEntity)).when(repository).findById(userModel.getId());
        doReturn(userModel).when(mapper).toModel(userEntity);

        //when
        final var result = service.delete(userModel.getId());

        //then
        assertEquals(userModel, result);
    }

    @Test
    public void loadUserByUsername_ifNotFoundUser_throwUsernameNotFoundException() {
        //given
        final var email = "ssss";
        doReturn(Optional.empty()).when(repository).findByEmail(email);

        //when/then
        assertThrows(UserNotFoundException.class, () -> service.loadUserByUsername(email));
    }

    @Test
    public void loadUserByUsername_ifFoundUser_successful() {
        //given
        final var userEntity = getUserEntity();
        final var userModel = getUserModel();
        doReturn(Optional.of(userEntity)).when(repository).findByEmail(userEntity.getEmail());
        doReturn(userModel).when(mapper).toModel(userEntity);

        //when
        final var result = service.loadUserByUsername(userEntity.getEmail());

        //then
        assertEquals(userModel, result);
    }
}
