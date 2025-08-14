package pro.sorokovsky.sorokchatserverspring.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pro.sorokovsky.sorokchatserverspring.entity.MessageEntity;
import pro.sorokovsky.sorokchatserverspring.model.MessageModel;

@Service
@RequiredArgsConstructor
public class MessageMapper {
    private final UserMapper userMapper;

    public MessageModel toModel(MessageEntity entity) {
        return MessageModel
                .builder()
                .id(entity.getId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .author(userMapper.toModel(entity.getAuthor()))
                .text(entity.getText())
                .build();
    }

    public MessageEntity toEntity(MessageModel model) {
        return MessageEntity
                .builder()
                .id(model.getId())
                .createdAt(model.getCreatedAt())
                .updatedAt(model.getUpdatedAt())
                .author(userMapper.toEntity(model.getAuthor()))
                .text(model.getText())
                .build();
    }
}
