package pro.sorokovsky.sorokchatserverspring.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pro.sorokovsky.sorokchatserverspring.entity.ChannelEntity;
import pro.sorokovsky.sorokchatserverspring.model.ChannelModel;

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
}
