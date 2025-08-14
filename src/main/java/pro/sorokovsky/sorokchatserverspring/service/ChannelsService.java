package pro.sorokovsky.sorokchatserverspring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.sorokovsky.sorokchatserverspring.contract.NewChannelPayload;
import pro.sorokovsky.sorokchatserverspring.contract.UpdateChannelPayload;
import pro.sorokovsky.sorokchatserverspring.exception.channel.ChannelNotFoundException;
import pro.sorokovsky.sorokchatserverspring.exception.user.UserNotFoundException;
import pro.sorokovsky.sorokchatserverspring.mapper.ChannelMapper;
import pro.sorokovsky.sorokchatserverspring.model.ChannelModel;
import pro.sorokovsky.sorokchatserverspring.model.UserModel;
import pro.sorokovsky.sorokchatserverspring.repository.ChannelsRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChannelsService {
    private final ChannelsRepository repository;
    private final ChannelMapper mapper;
    private final UsersService usersService;

    @Transactional(readOnly = true)
    public Optional<ChannelModel> getById(Long id) {
        return repository.findById(id).map(mapper::toModel);
    }

    @Transactional(readOnly = true)
    public List<ChannelModel> getByUserId(Long userId) {
        return repository.findAllByUsersId(userId).stream().map(mapper::toModel).collect(Collectors.toList());
    }

    @Transactional
    public ChannelModel create(UserModel user, NewChannelPayload payload) {
        final var model = mapper.toModel(payload);
        model.setUsers(List.of(user));
        final var created = repository.save(mapper.toEntity(model));
        return mapper.toModel(created);
    }

    @Transactional
    public ChannelModel update(long id, UpdateChannelPayload payload) {
        final var candidate = getById(id).orElseThrow(ChannelNotFoundException::new);
        final var merged = mapper.merge(payload, candidate);
        return mapper.toModel(repository.save(mapper.toEntity(merged)));
    }

    @Transactional
    public ChannelModel removeUser(long channelId, long userId) {
        final var channel = getById(channelId).orElseThrow(ChannelNotFoundException::new);
        channel.setUsers(channel.getUsers()
                .stream()
                .filter(user -> !user.getId().equals(userId)).collect(Collectors.toList()));
        final var updated = repository.save(mapper.toEntity(channel));
        return mapper.toModel(updated);
    }

    @Transactional
    public ChannelModel addUser(long channelId, long userId) {
        final var channel = getById(channelId).orElseThrow(ChannelNotFoundException::new);
        final var user = usersService.getById(userId).orElseThrow(UserNotFoundException::new);
        channel.getUsers().add(user);
        final var updated = repository.save(mapper.toEntity(channel));
        return mapper.toModel(updated);
    }

    @Transactional
    public ChannelModel delete(Long id) {
        final var candidate = repository.findById(id).map(mapper::toModel).orElseThrow(ChannelNotFoundException::new);
        repository.deleteById(id);
        return candidate;
    }
}
