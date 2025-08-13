package pro.sorokovsky.sorokchatserverspring.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pro.sorokovsky.sorokchatserverspring.contract.NewChannelPayload;
import pro.sorokovsky.sorokchatserverspring.contract.UpdateChannelPayload;
import pro.sorokovsky.sorokchatserverspring.entity.ChannelEntity;
import pro.sorokovsky.sorokchatserverspring.model.ChannelModel;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChannelMapper {
    private final UserMapper userMapper;
    private final MessageMapper messageMapper;

    public ChannelModel toModel(ChannelEntity entity) {
        return ChannelModel
                .builder()
                .id(entity.getId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .name(entity.getName())
                .description(entity.getDescription())
                .users(entity.getUsers().stream().map(userMapper::toModel).collect(Collectors.toList()))
                .messages(entity.getMessages().stream().map(messageMapper::toModel).collect(Collectors.toList()))
                .build();
    }

    public ChannelModel toModel(NewChannelPayload payload) {
        final var now = Date.from(Instant.now());
        return ChannelModel
                .builder()
                .createdAt(now)
                .updatedAt(now)
                .name(payload.name())
                .description(payload.description())
                .users(List.of())
                .messages(List.of())
                .build();
    }

    public ChannelModel merge(UpdateChannelPayload newState, ChannelModel oldState) {
        return ChannelModel
                .builder()
                .id(oldState.getId())
                .createdAt(oldState.getCreatedAt())
                .updatedAt(Date.from(Instant.now()))
                .name((newState.name() != null && !newState.name().isBlank()) ? newState.name() : oldState.getName())
                .description((newState.description() != null && !newState.description().isEmpty()) ? newState.description() : oldState.getDescription())
                .messages(oldState.getMessages())
                .users(oldState.getUsers())
                .build();
    }

    public ChannelEntity toEntity(ChannelModel model) {
        return ChannelEntity
                .builder()
                .id(model.getId())
                .createdAt(model.getCreatedAt())
                .updatedAt(model.getUpdatedAt())
                .name(model.getName())
                .description(model.getDescription())
                .messages(model.getMessages().stream().map(messageMapper::toEntity).collect(Collectors.toList()))
                .users(model.getUsers().stream().map(userMapper::toEntity).collect(Collectors.toList()))
                .build();
    }
}
