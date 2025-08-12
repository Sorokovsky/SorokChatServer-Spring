package pro.sorokovsky.sorokchatserverspring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
public class UsersService implements UserDetailsService {
    private final UsersRepository repository;
    private final UserMapper mapper;

    @Transactional(readOnly = true)
    public Optional<UserModel> getById(Long id) {
        return repository.findById(id).map(mapper::toModel);
    }

    @Transactional(readOnly = true)
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getByEmail(username).orElseThrow(UserNotFoundException::new);
    }
}
