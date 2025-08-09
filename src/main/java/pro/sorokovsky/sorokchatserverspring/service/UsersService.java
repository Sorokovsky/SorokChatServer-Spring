package pro.sorokovsky.sorokchatserverspring.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pro.sorokovsky.sorokchatserverspring.contract.NewUserPayload;
import pro.sorokovsky.sorokchatserverspring.contract.UpdateUserPayload;
import pro.sorokovsky.sorokchatserverspring.exception.UserAlreadyExistsException;
import pro.sorokovsky.sorokchatserverspring.exception.UserNotFoundException;
import pro.sorokovsky.sorokchatserverspring.mapper.UserMapper;
import pro.sorokovsky.sorokchatserverspring.model.UserModel;
import pro.sorokovsky.sorokchatserverspring.repository.UsersRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsersService {
    private final UsersRepository repository;
    private final UserMapper mapper;

    public Optional<UserModel> getById(Long id) {
        return repository.findById(id).map(mapper::toModel);
    }

    public Optional<UserModel> getByEmail(String email) {
        return repository.findByEmail(email).map(mapper::toModel);
    }

    @Transactional
    public UserModel create(NewUserPayload payload) {
        final var candidate = getByEmail(payload.email());
        if (candidate.isPresent()) throw new UserAlreadyExistsException();
        return mapper.toModel(repository.save(mapper.toEntity(payload)));
    }

    @Transactional
    public UserModel update(Long id, UpdateUserPayload payload) {
        final var candidate = getById(id).orElseThrow(UserNotFoundException::new);
        return mapper.toModel(repository.save(mapper.merge(candidate, payload)));
    }

    @Transactional
    public UserModel delete(Long id) {
        final var candidate = getById(id).orElseThrow(UserNotFoundException::new);
        repository.deleteById(id);
        return candidate;
    }
}
