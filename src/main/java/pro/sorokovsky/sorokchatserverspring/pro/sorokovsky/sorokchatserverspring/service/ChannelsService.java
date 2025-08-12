package pro.sorokovsky.sorokchatserverspring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.sorokovsky.sorokchatserverspring.mapper.ChannelMapper;
import pro.sorokovsky.sorokchatserverspring.model.ChannelModel;
import pro.sorokovsky.sorokchatserverspring.repository.ChannelsRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChannelsService {
    private final ChannelsRepository repository;
    private final ChannelMapper mapper;

    @Transactional(readOnly = true)
    public Optional<ChannelModel> getById(Long id) {
        return repository.findById(id).map(mapper::toModel);
    }

    @Transactional(readOnly = true)
    public List<ChannelModel> getByUserId(Long userId) {
        return repository.findAllByUsersId(userId).stream().map(mapper::toModel).collect(Collectors.toList());
    }
}
